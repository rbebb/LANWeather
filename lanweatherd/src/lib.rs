use std::ffi::CString;
use std::os::raw::c_char;

#[no_mangle] // don't mangle the name
pub extern "C" fn nws_req() -> *const c_char { // returns a pointer to a c char array
    let s = CString::new("succeed").unwrap(); // make a CString
    let p = s.as_ptr(); // get a pointer to the CString
    std::mem::forget(s); // don't have rust free the memory
    p // return the pointer
}