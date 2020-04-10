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
    char read_buf [128];
    memset(&read_buf, '\0', sizeof(read_buf));

    //Setup line buffer
    char line_buf [128];
    memset(&line_buf, '\0', sizeof(line_buf));

    //Setup temp & humid buffers
    char humid_buf [6];
    char temp_buf [6];
    memset(&humid_buf, '\0', sizeof(humid_buf));
    memset(&temp_buf, '\0', sizeof(temp_buf));

    //Other variable setup
    double humid = -1.0;
    double temp = -1.0;
    int count = 0;

    //TESTING AREA START
    double x = -1.0;
    char my_buf[] = {'3', '4', '.', '5', '7', '\0'};
    printf("Test1 atof: %f\n", x);
    x = atof(my_buf);
    printf("Test2 atof: %f\n", x);
    //TESTING AREA END

    while(true){

        int n = read(serial_port, &read_buf, sizeof(read_buf));

        for(int i=0;i<n;i++){
            if(read_buf[i]=='\n'){
                //Placeholder char arrays for temp & humid values
                //memcpy(humid_buf, &line_buf[13], sizeof(humid_buf)-sizeof(char));
                //memcpy(temp_buf, &line_buf[32], sizeof(temp_buf)-sizeof(char));
                for(int j=0;j<5;j++){
                    humid_buf[j] = line_buf[13+j];
                    temp_buf[j] = line_buf[32+j];
                }
                humid_buf[6] = '\0';
                temp_buf[6] = '\0';

                //TESTING print buffer values
                //printf("Test H Char: %c \n", line_buf[13]);
                //printf("Test T Char: %c \n", line_buf[32]);
                printf("Test H Buffer: \"%s\" \n", humid_buf);
                printf("Test T Buffer: \"%s\" \n", temp_buf);

                //Convert char arrays to doubles
                humid = atof(humid_buf);
                temp = atof(temp_buf);

                //TESTING print test values
                printf("H: \"%d\"\n", humid);
                printf("T: \"%d\"\n", temp);

                //Reset line_buf after we read the end of the line
                count = 0;
                memset(&line_buf, '\0', sizeof(line_buf));
            }
            else{
                //printf("Count: %d", count);
                line_buf[count] = read_buf[i];
                count++;
            }
        }
        //Reset read_buf after every read
        memset(&read_buf, '\0', sizeof(read_buf));
    }

    //END HERE-------------------------------------------------------------------------------------

    // cleanup
    close(serial_port);
    syslog(LOG_NOTICE, "stopping tempsensor");
    closelog();
    exit(EXIT_SUCCESS);
}
