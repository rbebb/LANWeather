/**
 * Singleton resources:
 * https://stackoverflow.com/a/1008289
 * http://www.modernescpp.com/index.php/thread-safe-initialization-of-a-singleton
**/

class recent_data {
    public:
        static recent_data& get_instance();

    private:
        recent_data();
};