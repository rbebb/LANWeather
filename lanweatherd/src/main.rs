use std::thread;

use nws_request::RecentData;
use nws_request::{nws_loop, sensor_loop};

fn main() {
    // TODO: pid loop


    // root (why?)


    // Initial logging

    //fork (forking doesn't seem necessary?)

    //file perms (777?)

    // Choose local storage or online database
    

    //lanweatherd functions here
    // Do we need to explictly create threads like C++?
    // We could store a reference to the RECENT_DATA singleton, but it doesn't look like we need to
    let mut recent_data: RecentData = RecentData {
        nws_data: String::new(),
        sensor_data: String::new(),
    };

    thread::spawn(|| {
        nws_loop(&mut recent_data);
    });
    thread::spawn(|| {
        sensor_loop(&mut recent_data);
    });

    // Cleanup
    // 1. Last syslog
    // 2. End logging
    // 3. exit
}