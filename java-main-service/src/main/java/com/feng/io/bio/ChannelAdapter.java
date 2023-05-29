package com.feng.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

public abstract class ChannelAdapter extends Thread {

    private Socket socket;
    private ChannelHandler channelHandler;
    private Charset charset;

    public ChannelAdapter(Socket socket, Charset charset) {
        this.socket = socket;
        this.charset = charset;
        System.out.println("socket.isConnected():" + socket.isConnected());
        while (!socket.isConnected()) {
            break;
        }
        channelHandler = new ChannelHandler(this.socket, charset);
        channelActive(channelHandler);
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), charset));
            String str = null;
            while ((str = input.readLine()) != null) {
                channelRead(this.channelHandler, str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 链接建立通知抽象方法
    public abstract void channelActive(ChannelHandler channelHandler);

    // 读取消息抽象方法
    public abstract void channelRead(ChannelHandler channelHandler, Object msg);
}
