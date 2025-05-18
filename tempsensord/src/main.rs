extern crate si7021;
extern crate i2cdev;

use i2cdev::linux::LinuxI2CDevice;
use si7021::{Si7021, SI7021_I2C_ADDRESS};

fn main() {
    println!("Hello, world!");

    //Open device via i2c
    let device = LinuxI2CDevice::new("/dev/i2c-1", SI7021_I2C_ADDRESS).unwrap();
    let mut si7021 = Si7021::new(device);

    //Type

}
