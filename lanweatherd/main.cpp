#include <cstdlib>
#include <cstring>
#include <stdlib.h>
#include <string>
#include <unistd.h>
#include <sys/stat.h>
#include <syslog.h>


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
    openlog("lan-weatherd", LOG_NOWAIT |  LOG_PID, LOG_USER);
    syslog(LOG_NOTICE, "started LAN Weather daemon");

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

    // close stdin, stdout, stderr
    close(STDIN_FILENO);
    close(STDOUT_FILENO);
    close(STDERR_FILENO);



    // cleanup
    syslog(LOG_NOTICE, "stopping lan-weatherd");
    closelog();
    exit(EXIT_SUCCESS);
}