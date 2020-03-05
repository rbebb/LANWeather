#include <pigpio.h>
#include <stdio.h>
#include <stdlib.h>

int main(){

    unsigned int x = gpioVersion();

    printf("Version: %d \n", x);

    if(gpioInitialise() < 0){
        printf("GPIO INITIALIZATION: FAILED");
        return 1;
    }
    else{
        printf("GPIO INITALIZATION: SUCCESS");
    }

    //Type here

    gpioTerminate();
    
}