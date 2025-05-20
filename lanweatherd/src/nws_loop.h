#include "recent_data.h"
#include <string>

extern "C" {
    char* nws_req(std::string latitude, std::string longitude);
}

void nws_loop(recent_data& cache, int& latitude, int& longitude);