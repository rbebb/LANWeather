#include <cstdlib>
#include <cstring>
#include <stdlib.h>
#include <string>
#include <unistd.h>
#include <sys/stat.h>
#include <syslog.h>
#include <thread>
#include <vector>
#include <fcntl.h>
#include <errno.h>
#include <termios.h>
#include <unistd.h>

#include "sensor_daemon.hpp"
//#include "libs/SerialPort-master/include/SerialPort.hpp"

using namespace std;

int *nop(void) {
    syslog(LOG_NOTICE, "called nop");
    return 0;
}

int main(void) {
    // TODO: check for existing instance of the daemon

    pid_t pid, sid;

    printf("Step 1 \n");

    pid = fork();

    if (pid > 0) {
        // it's the parent, it can exit
        printf("Step 2(Parent) \n");
        exit(EXIT_SUCCESS);
    } else if (pid < 0) {
        printf("Step 2(Error) \n");
        // there was an error forking, exit
        exit(EXIT_FAILURE);
    }

    // the child process continues from here
    printf("Step 3(Child) \n");

    // set default file permissions
    umask(0);

    // tap into the system log
    openlog("tempsensor", LOG_NOWAIT | LOG_PID, LOG_USER);
    syslog(LOG_NOTICE, "started Weather Temp/Humid sensor daemon");

    printf("Step 4 \n");

    // session id
    sid = setsid();
    if (sid < 0) {
        syslog(LOG_ERR, "could not generate session id");
        exit(EXIT_FAILURE);
    }

    printf("Step 5 \n");

    // move to the filesystem root
    if (chdir("/")) {
        syslog(LOG_ERR, "failed to move to /");
        exit(EXIT_FAILURE);
    }

    sleep(5);

    printf("Step 6 (That's all folks) \n");

    // close stdin, stdout, stderr
    //close(STDIN_FILENO);
    //close(STDOUT_FILENO);
    //close(STDERR_FILENO);

    //START HERE ----------------------------------------------------------------------------------

    int serial_port = open("/dev/ttyACM0", O_RDWR);

    if(serial_port < 0){
        printf("Error %i from open %s \n", errno, strerror(errno));
        exit(EXIT_FAILURE);
    }

    printf("Works!\n");

    //END HERE-------------------------------------------------------------------------------------

    // cleanup
    syslog(LOG_NOTICE, "stopping tempsensor");
    closelog();
    exit(EXIT_SUCCESS);
}
