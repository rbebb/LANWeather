# lanweatherd

The Linux daemon that coordinates the LAN Weather system.
It is responsible for compiling and distributing data.

## Install

Prerequisites

- [Rust](https://www.rust-lang.org/tools/install)
- [libzmq](https://github.com/zeromq/libzmq#installation-of-binary-packages-)

```bash
make build-release-lib
sudo make install-lib
make build-release
sudo make install
```

This set of steps firsts builds the Rust dynamic library for talking to the NWS API with release flags, installs it to `/usr/lib/`, builds the lanweatherd daemon with release flags linked to the installed library, then installs the daemon binary in `/usr/sbin` and registers it with systemd.
`sudo` is needed for the install steps.

## Development/Debugging

```bash
make build-debug
# THEN
make run-debug
# OR
sudo make run-debug
```

Running with `sudo` will allow the pidfile to be written, without it the pid printed to the terminal.

Python scripts in the `test_scripts/` directory can be used to test the publish and response capabilities of the API.
These require [pyzmq](https://github.com/zeromq/pyzmq#building-and-installation).
