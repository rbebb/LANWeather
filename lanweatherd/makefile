cxx-compiler = g++
cxx-linker = g++
rust-compiler = cargo build

cxx-build-dir = ./build/
rust-build-dir = ./target/

debug-dir = debug/
release-dir = release/
objects-dir = objects/

cxx-compiler-flags-debug = -Wall -g -std=c++11
cxx-compiler-flags-release = -w -O2 -std=c++11

cxx-linker-flags-debug = -lstdc++ -pthread -lzmq -L $(rust-build-dir)$(debug-dir) -lnws_request
cxx-linker-flags-release = -lstdc++ -pthread -lzmq -lnws_request

# no flags for rust debug/dev build
rust-compiler-flags-release = --release

cdo-dir = $(cxx-build-dir)$(debug-dir)$(objects-dir)
cro-dir = $(cxx-build-dir)$(release-dir)$(objects-dir)

lib = libnws_request.so
debug-objects = $(cdo-dir)recent_data.o $(cdo-dir)nws_loop.o $(cdo-dir)pub_loop.o $(cdo-dir)rep_loop.o $(cdo-dir)sensor_loop.o $(cdo-dir)main.o
release-objects = $(cro-dir)recent_data.o $(cro-dir)nws_loop.o $(cro-dir)pub_loop.o $(cro-dir)rep_loop.o $(cro-dir)sensor_loop.o $(cro-dir)main.o
executable = lanweatherd

build-debug: $(rust-build-dir)$(debug-dir)$(lib) $(debug-objects)
	$(cxx-linker) $^ $(cxx-linker-flags-debug) -o $(cxx-build-dir)$(debug-dir)$(executable)

$(cdo-dir)%.o: src/%.cpp
	mkdir -p $(cxx-build-dir)$(debug-dir)$(objects-dir)
	$(cxx-compiler) $(cxx-compiler-flags-debug) -c $^ -o $@

$(rust-build-dir)$(debug-dir)$(lib): src/lib.rs Cargo.toml
	$(rust-compiler)

run-debug: build-debug
	LD_LIBRARY_PATH=$(rust-build-dir)$(debug-dir) $(cxx-build-dir)$(debug-dir)$(executable)

build-release: /usr/lib/$(lib) $(release-objects)
	$(cxx-linker) $^ $(cxx-linker-flags-release) -o $(cxx-build-dir)$(release-dir)$(executable)

$(cro-dir)%.o: src/%.cpp
	mkdir -p $(cxx-build-dir)$(release-dir)$(objects-dir)
	$(cxx-compiler) $(cxx-compiler-flags-release) -c $^ -o $@

build-release-lib: src/lib.rs Cargo.toml
	$(rust-compiler) $(rust-compiler-flags-release)

install-lib: $(rust-build-dir)$(release-dir)$(lib)
	cp $(rust-build-dir)$(release-dir)$(lib) /usr/lib/
	ldconfig -v

install: /usr/lib/$(lib) $(cxx-build-dir)$(release-dir)$(executable)
	cp $(cxx-build-dir)$(release-dir)$(executable) /usr/sbin/
	cp lanweatherd.service /etc/systemd/system/
	chmod 644 /etc/systemd/system/lanweatherd.service
	systemctl daemon-reload
	systemctl enable lanweatherd
	systemctl start lanweatherd

clean:
	rm -rf $(cxx-build-dir) $(rust-build-dir)