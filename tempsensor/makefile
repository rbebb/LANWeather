CC = g++
LD = g++
CCFLAGS = -Wall
LDFLAGS = -lstdc++ -pthread -L ./target/debug/
EXE = tempsensor
OBJS = sensor_daemon.o

RM = /bin/rm


all: $(EXE)

$(EXE): $(OBJS)
	$(LD) $^ $(LDFLAGS) -o $@

%.o: src/%.cpp
	$(CC) $(CCFLAGS) -c $^ -o $@

clean:
	$(RM) -rf *.o $(EXE) target/