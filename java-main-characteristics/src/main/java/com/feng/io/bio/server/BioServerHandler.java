package com.feng.io.bio.server;

import com.feng.io.bio.ChannelAdapter;
import com.feng.io.bio.ChannelHandler;

import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BioServerHandler extends ChannelAdapter {


    public BioServerHandler(Socket socket, Charset charset) {
        super(socket, charset);
    }

    @Override
    public void channelActive(ChannelHandler channelHandler) {
        System.out.println("BioServer LocalAddress: " + channelHandler.socket().getLocalAddress());
        channelHandler.writeAndFlush("hi! this is BioServer, channel active \r\n");
    }

    @Override
    public void channelRead(ChannelHandler channelHandler, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        channelHandler.writeAndFlush(String.format("hi! this is BioServer, 我已经收到你的消息:%s. Success！\r\n", msg));
    }
}
