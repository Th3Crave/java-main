package com.feng.io.bio.client;

import com.feng.io.bio.ChannelAdapter;
import com.feng.io.bio.ChannelHandler;

import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BioClientHandler extends ChannelAdapter {

    public BioClientHandler(Socket socket, Charset charset) {
        super(socket, charset);
    }

    @Override
    public void channelActive(ChannelHandler channelHandler) {
        System.out.println("BioClient LocalAddress: " + channelHandler.socket().getInetAddress());
        channelHandler.writeAndFlush("hi! this is BioClient, channel active \r\n");
    }

    @Override
    public void channelRead(ChannelHandler channelHandler, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        channelHandler.writeAndFlush(String.format("hi! this is BioClient, 我已经收到你的消息:%s. Success！\r\n", msg));
    }
}
