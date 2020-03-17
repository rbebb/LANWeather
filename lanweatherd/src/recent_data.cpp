/**
 * Singleton resources:
 * https://stackoverflow.com/a/1008289
 * http://www.modernescpp.com/index.php/thread-safe-initialization-of-a-singleton
**/

class recent_data {
    public:
        static recent_data& get_instance() {
            static recent_data instance;
            return instance;
        }

    private:
        recent_data() {}

    public:
        recent_data(recent_data const&) = delete;
        void operator = (recent_data const&) = delete;
}