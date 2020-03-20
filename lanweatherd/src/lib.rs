extern crate json;
extern crate reqwest;

use std::ffi::CString;
use std::os::raw::c_char;
use json::object;

#[no_mangle] // don't mangle the name
pub extern "C" fn nws_req() -> *const c_char { // returns a pointer to a c char array
    let s = call_api().unwrap();
    let p = s.as_ptr(); // get a pointer to the CString
    std::mem::forget(s); // don't have rust free the memory
    return p; // return the pointer
}

fn call_api() -> Result<CString, reqwest::Error> {
    // TODO: get accurate box
    let client = reqwest::blocking::Client::builder()
        .user_agent("(lanweather, castlet1@wit.edu")
        .build()?;
    let current = client.get("https://api.weather.gov/gridpoints/BOX/69,75").send()?.text()?;
    let dforecast = client.get("https://api.weather.gov/gridpoints/BOX/69,75/forecast?units=si").send()?.text()?;
    let hforecast = client.get("https://api.weather.gov/gridpoints/BOX/69,75/forecast/hourly?units=si").send()?.text()?;

    let parsed_current = json::parse(&current).unwrap();
    let parsed_dforecast = json::parse(&dforecast).unwrap();
    let parsed_hforecast = json::parse(&hforecast).unwrap();

    // build new json from desired parts of old json
    let useful = object!{
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
        },
        "daily": {
            "periods": parsed_dforecast["properties"]["periods"].clone()
        },
        "hourly": {
            "periods": parsed_hforecast["properties"]["periods"].clone()
        }
    };

    let s = CString::new(useful.dump()).unwrap();
    return Ok(s);
}