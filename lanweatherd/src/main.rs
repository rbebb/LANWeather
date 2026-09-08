use std::thread;
use std::sync::{Arc, Mutex};

use nws_request::RecentData;
use nws_request::{nws_loop, pub_loop, rep_loop, sensor_loop};

fn main() {
    // TODO: pid loop


    // root (why?)


    // Initial logging

    //fork (forking doesn't seem necessary?)

    //file perms (777?)

    // Choose local storage or online database
    

    // lanweatherd functions here
    // Do we need to explictly create threads like C++?
    // We could store a reference to the RECENT_DATA singleton, but it doesn't look like we need to
    let mut recent_data: RecentData = RecentData {
        nws_data: String::new(),
        sensor_data: String::new(),
    };
    let recent_data_safe = Arc::new(Mutex::new(recent_data));

    // We'll try using an Arc and Mutex to share mutable state and enable
    // thread-safe access, but for now that means only thread can modify
    // the RecentData object at a time.

    let mut handles = vec![];

    let recent_data_nws_loop = Arc::clone(&recent_data_safe);
    handles.push(
        thread::spawn(move || {
            let mut recent = recent_data_nws_loop.lock().unwrap();
            nws_loop(&mut recent);
        })
    );
    let recent_data_sensor_loop = Arc::clone(&recent_data_safe);
    handles.push(
        thread::spawn(move || {
            let mut recent = recent_data_sensor_loop.lock().unwrap();
            sensor_loop(&mut recent);
        })
    );
    let recent_data_pub_loop = Arc::clone(&recent_data_safe);
    handles.push(
        thread::spawn(move || {
            let mut recent = recent_data_pub_loop.lock().unwrap();
            pub_loop(&mut recent);
        })
    );
    let recent_data_rep_loop = Arc::clone(&recent_data_safe);
    handles.push(
        thread::spawn(move || {
            let mut recent = recent_data_rep_loop.lock().unwrap();
            rep_loop(&mut recent);
        })
    );

    // We probably need a better to handle turning off the service
    // for handle in handles {
    //     handle.join().unwrap();
    // }

    // Cleanup
    // 1. Last syslog
    // 2. End logging
    // 3. exit
}