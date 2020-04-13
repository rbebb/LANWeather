#include <unistd.h>
#include <zmq.h>
#include <stdlib.h>
#include <syslog.h>

#include "sensor_loop.h"

void sensor_loop(recent_data& cache){
    void *context = zmq_ctx_new();
    void *sock = zmq_socket(context, ZMQ_SUB);
    zmq_bind(sock, "tcp://*:5676");
    zmq_setsockopt(sock, ZMQ_SUBSCRIBE, "", 0);

    while(1) {
        char* buffer = (char*) malloc(64 * sizeof(char));
        zmq_recv(sock, buffer, 63, 0);
        cache.update_sensor_data(buffer);
    }

    zmq_close(sock);
    zmq_ctx_destroy(context);
}