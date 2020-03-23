#include <unistd.h>

#include "nws_loop.h"

int NWS_SLEEP_INTERVAL = 60 * 15; // every 15 minutes

void nws_loop(recent_data& cache) {
    while (1) {
        char* response = nws_req();
        cache.update_nws_data(response);

        sleep(NWS_SLEEP_INTERVAL);
    }
}