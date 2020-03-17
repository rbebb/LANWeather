/**
 * Singleton resources:
 * https://stackoverflow.com/a/1008289
 * http://www.modernescpp.com/index.php/thread-safe-initialization-of-a-singleton
**/

#pragma once
#include <string>

class recent_data {
    public:
        static recent_data& get_instance();
        void update_nws_data(char* new_data);
        void update_sensor_data(char* new_data);
        char* get_data_bundle();

    private:
        recent_data();
        char* nws_data;
        char* sensor_data;
};