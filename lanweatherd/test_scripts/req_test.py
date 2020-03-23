import zmq

ctx = zmq.Context()

# pylint: disable=E1101
socket = ctx.socket(zmq.REQ)

socket.connect("tcp://localhost:5680")

socket.send(b"launch the nukes")
data = socket.recv(0, True, False)
print(data)

socket.close()
ctx.destroy()