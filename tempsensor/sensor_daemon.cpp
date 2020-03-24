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
    pid = fork();

    if (pid > 0) {
        // it's the parent, it can exit
        exit(EXIT_SUCCESS);
    } else if (pid < 0) {
        // there was an error forking, exit
        exit(EXIT_FAILURE);
    }

    // the child process continues from here

    // set default file permissions
    umask(0);

    // tap into the system log
    openlog("tempsensor", LOG_NOWAIT | LOG_PID, LOG_USER);
    syslog(LOG_NOTICE, "started Weather Temp/Humid sensor daemon");

    // session id
    sid = setsid();
    if (sid < 0) {
        syslog(LOG_ERR, "could not generate session id");
        exit(EXIT_FAILURE);
    }

    // move to the filesystem root
    if (chdir("/")) {
        syslog(LOG_ERR, "failed to move to /");
        exit(EXIT_FAILURE);
    }

    // close stdin, stdout, stderr (open rn for debugging)
    //close(STDIN_FILENO);
    //close(STDOUT_FILENO);
    //close(STDERR_FILENO);

    //START HERE ----------------------------------------------------------------------------------

    //Really long Serial Port Setup code
    int serial_port = open("/dev/ttyACM0", O_RDWR);

    if(serial_port < 0){
        printf("Error %i from open %s \n", errno, strerror(errno));
        exit(EXIT_FAILURE);
    }

    struct termios tty;

    memset(&tty, 0, sizeof tty);

    if(tcgetattr(serial_port, &tty) != 0){
        printf("Error %i from tcgetattr: %s \n", errno, strerror(errno));
    }

    tty.c_cflag &= ~PARENB;
    tty.c_cflag &= ~CSTOPB;
    tty.c_cflag |= CS8;
    tty.c_cflag &= ~CRTSCTS;
    tty.c_cflag |= CREAD | CLOCAL;

    tty.c_lflag &= ~ICANON;
    tty.c_lflag &= ~ECHO;
    tty.c_lflag &= ~ECHOE;
    tty.c_lflag &= ~ECHONL;
    tty.c_lflag &= ~ISIG;

    tty.c_iflag &= ~(IXON | IXOFF | IXANY);
    tty.c_iflag &= ~(IGNBRK|BRKINT|PARMRK|ISTRIP|INLCR|IGNCR|ICRNL);

    tty.c_oflag &= ~OPOST;
    tty.c_oflag &= ~ONLCR;

    tty.c_cc[VTIME] = 10;
    tty.c_cc[VMIN] = 0;

    cfsetispeed(&tty, B115200);
    cfsetospeed(&tty, B115200);

    if (tcsetattr(serial_port, TCSANOW, &tty) != 0) {
        printf("Error %i from tcsetattr: %s\n", errno, strerror(errno));
    }

    //Setup a read buffer
    char read_buf [256];
    memset(&read_buf, '\0', sizeof(read_buf));

    while(1){

        //sleep(.1);

        //Read from serial_port and store it in the buffer
        int n = read(serial_port, &read_buf, sizeof(read_buf));

        //Print to stdout if there is new data in the buffer
        if(n != 0){
            //printf("Status: %d \n", n);
            printf("%s", read_buf);
            printf("\n");
            printf("\n");
        }

    }

    //END HERE-------------------------------------------------------------------------------------

    // cleanup
    close(serial_port);
    syslog(LOG_NOTICE, "stopping tempsensor");
    closelog();
    exit(EXIT_SUCCESS);
}
