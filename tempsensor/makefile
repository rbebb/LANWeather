CC = g++
LD = g++
CCFLAGS = -Wall -pthread
LDFLAGS = -lpigpio -lrt
EXE = temp_sensor
OBJS = si7021.o

all: $(EXE)

$(EXE): $(OBJS)
	$(LD) $^ $(LDFLAGS) -o $@

%.o: %.cpp
	$(CC) $(CCFLAGS) -c $^ -o $@

clean:
	$(RM) -rf *.o $(EXE)
