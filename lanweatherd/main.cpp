#include <cstdlib>
#include <cstring>
#include <stdlib.h>
#include <string>
#include <unistd.h>
#include <sys/stat.h>
#include <syslog.h>
#include <thread>
#include <vector>

#include "main.h"

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
    openlog("lanweatherd", LOG_NOWAIT | LOG_PID, LOG_USER);
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

    char* response = nws_req();
    syslog(LOG_NOTICE, response);

    vector<thread> threads;

    thread thr_nws_fetch(nop);
    threads.push_back(move(thr_nws_fetch)); // n.b. thread objects can't be copied

    thread thr_sensors_recv(nop);
    threads.push_back(move(thr_sensors_recv));

    thread thr_bcast_all(nop);
    threads.push_back(move(thr_bcast_all));

    thread thr_req_manager(nop);
    threads.push_back(move(thr_req_manager));

    for (unsigned int i = 0; i < threads.size(); i++) {
        threads[i].join();
    }

    // cleanup
    syslog(LOG_NOTICE, "stopping lanweatherd");
    closelog();
    exit(EXIT_SUCCESS);
}
