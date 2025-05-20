#include <string>
#include <unistd.h>

#include "nws_loop.h"

int NWS_SLEEP_INTERVAL = 60 * 20; // every 20 minutes

void nws_loop(recent_data& cache, int& latitude, int& longitude) {
    while (1) {
        char* response = nws_req(
            std::to_string(latitude),
            std::to_string(longitude)
        );
        cache.update_nws_data(response);

        sleep(NWS_SLEEP_INTERVAL);
    }
}