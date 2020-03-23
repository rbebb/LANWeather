#include "Adafruit_Si7021.h"
#include <pigpio.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

//Function prototypes
//uint8_t readRegisterByte(unsigned int h, uint8_t reg);
//void readSerialNumber(unsigned int h);

int main(){

    unsigned int handle = NULL;
    unsigned int retVal = NULL;

    //Initalize Raspberry Pi GPIO pins
    if(gpioInitialise() < 0){
        printf("GPIO INITIALIZATION: FAILED\n");
        return 1;
    }
    else{
        printf("GPIO INITALIZATION: SUCCESS\n");
    }

    //Open a connection to the SI7021 sensor over an i2c connection & store the handle
    handle = i2cOpen(1, SI7021_DEFAULT_ADDRESS, 0);
    if(handle >= 0){
        printf("I2C Status: OK\n");
    }

    Adafruit_Si7021 sensor = Adafruit_Si7021(handle);

    printf("Found model ");
    switch(sensor.getModel()) {
        case SI_Engineering_Samples:
            printf("SI engineering samples"); break;
        case SI_7013:
            printf("Si7013"); break;
        case SI_7020:
            printf("Si7020"); break;
        case SI_7021:
            printf("Si7021"); break;
        case SI_UNKNOWN:
        default:
            printf("Unknown");
    }

    printf(" Rev(");
    printf("%d", sensor.getRevision());
    printf(")");
    printf(" Serial #"); 
    printf("%x", sensor.sernum_a); 
    printf("%x \n", sensor.sernum_b);

    /*
    //Reset the sensor
    if(i2cWriteByte(handle, SI7021_RESET_CMD) != 0){
        printf("Error Resetting Sensor(1)\n");
    }
    if(readRegisterByte(handle, SI7021_READRHT_REG_CMD) != 0x3A){
        printf("Error Resetting Sensor(2)\n");
    }

    //Print out Serial Number
    //readSerialNumber(handle);

    i2cClose(handle);
    gpioTerminate();
    */ 

    //---------------------------------------------------------------------------------------------
    //Work here

    //Type

}

//-------------------------------------------------------------------------------------------------
//TODO: Document all this later

/*
uint8_t readRegisterByte(unsigned int h, uint8_t reg){
    uint8_t returnVal;
    i2cWriteByte(h, reg);
    usleep(500);
    returnVal = i2cReadByte(h);
    return returnVal;
}

void readSerialNumber(unsigned int h){
    uint32_t numA;
    uint32_t numB;

    i2cWriteByte(h, (SI7021_ID1_CMD >> 8));
    i2cWriteByte(h, (SI7021_ID1_CMD & 0xFF));

    usleep(500);

    numA = i2cReadByte(h);
    i2cReadByte(h);
    numA <<= 8;
    numA |= i2cReadByte(h);
    i2cReadByte(h);
    numA <<= 8;
    numA |= i2cReadByte(h);
    i2cReadByte(h);
    numA <<= 8;
    numA |= i2cReadByte(h);
    i2cReadByte(h);

    i2cWriteByte(h, (SI7021_ID2_CMD >> 8));
    i2cWriteByte(h, (SI7021_ID2_CMD & 0xFF));

    usleep(500);

    numB = i2cReadByte(h);
    i2cReadByte(h);
    numB <<= 8;
    numB |= i2cReadByte(h);
    i2cReadByte(h);
    numB <<= 8;
    numB |= i2cReadByte(h);
    i2cReadByte(h);
    numB <<= 8;
    numB |= i2cReadByte(h);
    i2cReadByte(h);

    switch(numB >> 24){
        case 0:
        case 0xff:
            printf("SI_Engineering_Samples\n");
            break;
        case 0x0D:
            printf("SI_7013\n");
            break;
        case 0x14:
            printf("SI_7020\n");
            break;
        case 0x15:
            printf("SI_7021\n");
            break;
        default:
            printf("SI_UNKNOWN\n");
    }

}
*/
