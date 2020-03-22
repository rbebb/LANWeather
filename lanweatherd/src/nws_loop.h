#include "recent_data.h"

extern "C" {
    char* nws_req(void);
}

void nws_loop(recent_data& cache);