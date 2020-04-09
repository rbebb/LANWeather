#include <unistd.h>
#include <cstdlib>
#include <cstring>
#include <zmq.h>

#include "pub_loop.h"

int PUB_PRE_SLEEP_INTERVAL = 60; // publish one minute after starting so there's data
int PUB_SLEEP_INTERVAL = 60 * 59; // then every hour afterwards

void pub_loop(recent_data& cache) {
    void *context = zmq_ctx_new();
    void *publisher = zmq_socket(context, ZMQ_PUB);
    zmq_bind(publisher, "tcp://*:5670");

    while (1) {
        sleep(PUB_PRE_SLEEP_INTERVAL);
        char* bundle = cache.get_data_bundle();
        int msg_len = strlen(bundle);
        zmq_send(publisher, bundle, msg_len, 0);
        free(bundle);
        sleep(PUB_SLEEP_INTERVAL);
    }

    zmq_close(publisher);
    zmq_ctx_destroy(context);
}