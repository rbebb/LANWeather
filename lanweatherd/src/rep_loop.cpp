#include <unistd.h>
#include <cstdlib>
#include <cstring>
#include <zmq.h>

#include "rep_loop.h"

void rep_loop(recent_data& cache) {
    void *context = zmq_ctx_new();
    void *responder = zmq_socket(context, ZMQ_REP);
    zmq_bind(responder, "tcp://*:5680");

    while (1) {
        char buffer[256];
        zmq_recv(responder, buffer, 255, 0);

        char* bundle = cache.get_data_bundle();
        int msg_len = strlen(bundle);

        zmq_send(responder, bundle, msg_len, 0);
        free(bundle);
    }

    zmq_close(responder);
    zmq_ctx_destroy(context);
}