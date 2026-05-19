extern crate json;
extern crate reqwest;

use std::ffi::CString;
use std::os::raw::c_char;
use json::object;


// Imports from WIP code
/////////
use std::ffi::CStr;
use std::ffi::CString;
use std::os::raw::c_char;
/////////


#[no_mangle] // don't mangle the name
pub extern "C" fn nws_req() -> *const c_char { // returns a pointer to a c char array
    let s = call_api().unwrap();
    let p = s.as_ptr(); // get a pointer to the CString
    std::mem::forget(s); // don't have rust free the memory so c can use it
    return p; // return the pointer
}

fn call_api() -> Result<CString, reqwest::Error> {
    let client = reqwest::blocking::Client::builder()
        .user_agent("(lanweather, castlet1@wit.edu)")
        .build()?;
    let current = client.get("https://api.weather.gov/gridpoints/BOX/72,51").send()?.text()?;
    let dforecast = client.get("https://api.weather.gov/gridpoints/BOX/72,51/forecast?units=si").send()?.text()?;
    let hforecast = client.get("https://api.weather.gov/gridpoints/BOX/72,51/forecast/hourly?units=si").send()?.text()?;

    // some naïve error checking
    let parsed_current = if current.as_bytes()[0] as char == '{' {json::parse(&current).unwrap()} else {json::parse("{\"bad\": true}").unwrap()};
    let parsed_dforecast = if dforecast.as_bytes()[0] as char == '{' {json::parse(&dforecast).unwrap()} else {json::parse("{\"bad\": true}").unwrap()};
    let parsed_hforecast = if hforecast.as_bytes()[0] as char == '{' {json::parse(&hforecast).unwrap()} else {json::parse("{\"bad\": true}").unwrap()};

    // grab the parts we want
    let current_values = if !parsed_current.has_key("bad") {object!{
        "current": {
            "temperature": parsed_current["properties"]["temperature"]["values"][0]["value"].clone(),
            "maxTemperature": parsed_current["properties"]["maxTemperature"]["values"][0]["value"].clone(),
            "minTemperature": parsed_current["properties"]["minTemperature"]["values"][0]["value"].clone(),
            "relativeHumidity": parsed_current["properties"]["relativeHumidity"]["values"][0]["value"].clone(),
            "windChill": parsed_current["properties"]["windChill"]["values"][0]["value"].clone(),
            "windDirection": parsed_current["properties"]["windDirection"]["values"][0]["value"].clone(),
            "windSpeed": parsed_current["properties"]["windSpeed"]["values"][0]["value"].clone(),
            "windGust": parsed_current["properties"]["windGust"]["values"][0]["value"].clone(),
            "weather": {
                "coverage": parsed_current["properties"]["weather"]["values"][0]["value"][0]["coverage"].clone(),
                "weather": parsed_current["properties"]["weather"]["values"][0]["value"][0]["weather"].clone(),
                "intensity": parsed_current["properties"]["weather"]["values"][0]["value"][0]["intensity"].clone(),
                "visibility": parsed_current["properties"]["weather"]["values"][0]["value"][0]["visibility"]["value"].clone()
            },
            "probabilityOfPrecipitation": parsed_current["properties"]["probabilityOfPrecipitation"]["values"][0]["value"].clone()
        }
    }} else {object!{
        "current": false
    }};

    let daily_values = if !parsed_dforecast.has_key("bad") {object!{
        "daily": {
            "periods": parsed_dforecast["properties"]["periods"].clone()
        }
    }} else {object!{
        "daily": false
    }};

    let hourly_values = if !parsed_hforecast.has_key("bad") {object!{
        "hourly": {
            "periods": parsed_hforecast["properties"]["periods"].clone()
        }
    }} else {object!{
        "hourly": false
    }};

    // package everything into one object
    let mut package = json::JsonValue::new_object();
    package.insert("current", current_values["current"].clone());
    package.insert("hourly", hourly_values["hourly"].clone());
    package.insert("daily", daily_values["daily"].clone());

    let s = CString::new(package.dump()).unwrap();
    return Ok(s);
}



// Start of C++ rewrite
/////////////////////////////////////////

// main.cpp



// new_loop.cpp



// pub_loop.cpp



// recent_data.cpp
struct recent_data {
    nws_data: String,
    sensor_data: String
}
impl recent_data {
    fn update_nws_data(ptr: *const c_char) {
        if let Ok(new_data) = CStr::from_ptr(ptr).to_str() {
            self.nws_data = new_data;
        }
    }
    fn update_sensor_data(ptr: *const c_char) {
        if let Ok(new_data) = CStr::from_ptr(ptr).to_str() {
            self.sensor_data = new_data;
        }
    }
    fn get_data_bundle() -> *mut c_char {
        // {\"nws\":%,\"sensor\":%}
        let msg = format!(
            "{\"nws\":{},\"sensor\":{}}",
            self.nws_data,
            self.sensor_data
        );
        CString::new(msg).into_raw();
    }
}
static mut RECENT_DATA: recent_data = recent_data {
    new_data: new_data,
    sensor_data: sensor_data
}


// Example code from Rust book
// Reference: https://doc.rust-lang.org/stable/embedded-book/peripherals/singletons.html#how-do-we-do-this-in-rust
struct Peripherals {
    serial: Option<SerialPort>,
}
impl Peripherals {
    fn take_serial(&mut self) -> SerialPort {
        let p = replace(&mut self.serial, None);
        p.unwrap()
    }
}
static mut PERIPHERALS: Peripherals = Peripherals {
    serial: Some(SerialPort),
};


// rep_loop.cpp



// sensor_loop.cpp
