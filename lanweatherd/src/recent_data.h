/**
 * Singleton resources:
 * https://stackoverflow.com/a/1008289
 * http://www.modernescpp.com/index.php/thread-safe-initialization-of-a-singleton
**/

#ifndef _RECENT_DATA_H_
#define _RECENT_DATA_H_
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

    public: // these are technically implementation i think but can't be out of declaration.
        recent_data(recent_data const&) = delete;
        void operator = (recent_data const&) = delete;
};
#endif