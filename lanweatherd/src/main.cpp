#include <cstdlib>
#include <cstring>
#include <stdlib.h>
#include <string>
#include <unistd.h>
#include <sys/stat.h>
#include <syslog.h>
#include <thread>
#include <vector>
#include <stdio.h>
#include <fcntl.h>

#include "main.h"
#include "recent_data.h"
#include "nws_loop.h"
#include "pub_loop.h"
#include "rep_loop.h"

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
        // it's the parent, write the pidfile and exit
        int pidfile;

        if ((pidfile = open("/var/run/lanweatherd.pid", O_WRONLY|O_CREAT|O_TRUNC, 0644)) == -1) {
            // if not launched as root, print pid to the terminal
            close(pidfile);
            printf("%d\n", pid);
            exit(EXIT_SUCCESS);
        }

        char pidstring[16];
        sprintf(pidstring, "%d", pid);
        write(pidfile, pidstring, strlen(pidstring));

        exit(EXIT_SUCCESS);
    } else if (pid < 0) {
        // there was an error forking, exit
        printf("error forking\n");
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

    recent_data& cache = recent_data::get_instance();

    vector<thread> threads;

    thread thr_nws_fetch(nws_loop, ref(cache));
    threads.push_back(move(thr_nws_fetch)); // n.b. thread objects can't be copied

    thread thr_sensors_recv(nop);
    threads.push_back(move(thr_sensors_recv));

    thread thr_bcast_all(pub_loop, ref(cache));
    threads.push_back(move(thr_bcast_all));

    thread thr_req_manager(rep_loop, ref(cache));
    threads.push_back(move(thr_req_manager));

    for (unsigned int i = 0; i < threads.size(); i++) {
        threads[i].join();
    }

    // cleanup
    syslog(LOG_NOTICE, "stopping lanweatherd");
    closelog();
    exit(EXIT_SUCCESS);
}
