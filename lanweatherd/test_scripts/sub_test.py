import zmq

ctx = zmq.Context()

# pylint: disable=E1101
socket = ctx.socket(zmq.SUB)

socket.connect("tcp://localhost:5670")
socket.setsockopt(zmq.SUBSCRIBE, b"")

data = socket.recv(0, True, False)
print(data)

socket.close()
ctx.destroy()