CC = g++
LD = g++
CCFLAGS = -Wall
LDFLAGS = -lstdc++
EXE = lanweatherd
OBJS = main.o

RM = /bin/rm


all: $(EXE)

$(EXE): $(OBJS)
	$(LD) $^ $(LDFLAGS) -o $@

%.o: %.cpp
	$(CC) $(CCFLAGS) -c $^ -o $@


clean:
	$(RM) -rf *.o $(EXE)