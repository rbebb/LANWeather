/**
 * Singleton resources:
 * https://stackoverflow.com/a/1008289
 * http://www.modernescpp.com/index.php/thread-safe-initialization-of-a-singleton
**/

#include <cstdlib>
#include <cstring>

#include "recent_data.h"

recent_data& recent_data::get_instance() {
    static recent_data instance;
    return instance;
}

void recent_data::update_nws_data(char* new_data) {
    free(this->nws_data);
    this->nws_data = (char*) malloc(strlen(new_data) * sizeof(char));
    strcpy(this->nws_data, new_data);
    free(new_data);
}

void recent_data::update_sensor_data(char* new_data) {
    free(this->sensor_data);
    this->sensor_data = (char*) malloc(strlen(new_data) * sizeof(char));
    strcpy(this->sensor_data, new_data);
    free(new_data);
}

char* recent_data::get_data_bundle() {
    // {\"nws\":%,\"sensor\":%}
    char* msg = (char*) malloc((strlen(this->nws_data) * sizeof(char)) + (strlen(this->sensor_data) * sizeof(char)) + 18); // 18 is the number of extra characters added

    strcpy(msg, "{\"nws\":");
    strcat(msg, this->nws_data);
    strcat(msg, ",\"sensor\":");
    strcat(msg, this->sensor_data);
    strcat(msg, "}");

    return msg;
}

recent_data::recent_data() {
    this->nws_data = (char*) malloc(sizeof(char));
    this->nws_data[0] = '\0';
    this->sensor_data = (char*) malloc(sizeof(char));
    this->sensor_data = '\0';
}
