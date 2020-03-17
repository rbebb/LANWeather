CC = g++
LD = g++
CCFLAGS = -Wall
LDFLAGS = -lstdc++ -pthread -L ./target/debug/ -lnws_request
EXE = lanweatherd
OBJS = main.o
RUSTLIB = target/debug/libnws_request.so

RM = /bin/rm


all: $(EXE)

$(EXE): $(RUSTLIB) $(OBJS)
	$(LD) $^ $(LDFLAGS) -o $@

%.o: %.cpp
	$(CC) $(CCFLAGS) -c $^ -o $@

$(RUSTLIB): src/lib.rs Cargo.toml
	cargo build

clean:
	$(RM) -rf *.o $(EXE)