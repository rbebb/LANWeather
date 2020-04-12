#include <unistd.h>
#include <zmq.h>

#include "sensor_loop.h"

void sensor_loop(recent_data& cache){
    void *context = zmq_ctx_new();
    void *responder = zmq_socket(context, ZMQ_REP);
    zmq_bind(responder, "tcp://*:5675");

    while(1){
        char buffer[64];
        zmq_recv(responder, buffer, 63, 0);
        cache.update_sensor_data(buffer);
    }

    zmq_close(responder);
    zmq_ctx_destroy(context);
}