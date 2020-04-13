package com.example.lanweather.zmq;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class zmq {

    public static String getData() throws Exception {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            socket.connect("tcp://lw.minifigone.com:5675");

            String request = "fire the nukes";
            socket.send(request.getBytes(ZMQ.CHARSET), 0);

            byte[] reply = socket.recv(0);
            String result = new String(reply, ZMQ.CHARSET);

            return result;
        }
    }
}
