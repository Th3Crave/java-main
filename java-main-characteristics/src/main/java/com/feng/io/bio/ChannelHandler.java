package com.feng.io.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

// https://github.com/fuzhengwei/itstack-demo-netty.git


public class ChannelHandler {

    private Socket socket;
    private Charset charset;

    public ChannelHandler(Socket socket, Charset charset) {
        this.socket = socket;
        this.charset = charset;
    }

    public Socket socket() {
        return this.socket;
    }

    public void writeAndFlush(Object msg) {
        OutputStream outputStream;
        try {
            outputStream = socket.getOutputStream();
            outputStream.write((msg.toString()).getBytes(charset));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
