use std::ffi::CString;
use std::os::raw::c_char;

#[no_mangle]
pub extern "C" fn nws_req() -> *const c_char {
    let s = CString::new("succeed").unwrap();
    let p = s.as_ptr();
    std::mem::forget(s);
    p
}