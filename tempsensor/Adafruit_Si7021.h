/*!
 *  @file Adafruit_Si7021.h
 *
 *  This is a library for the Adafruit Si7021 breakout board.
 *
 *  Designed specifically to work with the Adafruit Si7021 breakout board.
 *
 *  Pick one up today in the adafruit shop!
 *  ------> https://www.adafruit.com/product/3251
 *
 *  These sensors use I2C to communicate, 2 pins are required to interface.
 *
 *  Adafruit invests time and resources providing this open source code,
 *  please support Adafruit andopen-source hardware by purchasing products
 *  from Adafruit!
 *
 *  Limor Fried (Adafruit Industries)
 *
 *  BSD license, all text above must be included in any redistribution
 */

#ifndef __Si7021_H__
#define __Si7021_H__

/*!
 *  I2C ADDRESS/BITS
 */
#define SI7021_DEFAULT_ADDRESS	0x40

#define SI7021_MEASRH_HOLD_CMD           0xE5 /**< Measure Relative Humidity, Hold Master Mode */
#define SI7021_MEASRH_NOHOLD_CMD         0xF5 /**< Measure Relative Humidity, No Hold Master Mode */
#define SI7021_MEASTEMP_HOLD_CMD         0xE3 /**< Measure Temperature, Hold Master Mode */
#define SI7021_MEASTEMP_NOHOLD_CMD       0xF3 /**< Measure Temperature, No Hold Master Mode */
#define SI7021_READPREVTEMP_CMD          0xE0 /**< Read Temperature Value from Previous RH Measurement */
#define SI7021_RESET_CMD                 0xFE /**< Reset Command */
#define SI7021_WRITERHT_REG_CMD          0xE6 /**< Write RH/T User Register 1 */
#define SI7021_READRHT_REG_CMD           0xE7 /**< Read RH/T User Register 1 */
#define SI7021_WRITEHEATER_REG_CMD       0x51 /**< Write Heater Control Register */
#define SI7021_READHEATER_REG_CMD        0x11 /**< Read Heater Control Register */
#define SI7021_ID1_CMD                   0xFA0F /**< Read Electronic ID 1st Byte */
#define SI7021_ID2_CMD                   0xFCC9 /**< Read Electronic ID 2nd Byte */
#define SI7021_FIRMVERS_CMD              0x84B8 /**< Read Firmware Revision */

#define SI7021_REV_1					0xff  /**< Sensor revision 1 */
#define SI7021_REV_2					0x20  /**< Sensor revision 2 */

/** An enum to represent sensor types **/
enum si_sensorType {
  SI_Engineering_Samples,
  SI_7013,
  SI_7020,
  SI_7021,
  SI_UNKNOWN,
};

#endif // __Si7021_H__
