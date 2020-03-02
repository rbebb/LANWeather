#include <pigpio.h>

int main(){

    if(gpioInitialize() < 0){
        print("GPIO INITIALIZATION: FAILED")
        return 1;
    }
    else{
        print("GPIO INITALIZATION: SUCCESS")
    }

    //Type here

    gpioTerminate();

}