package com.feng.io.nio.server;

import com.feng.io.nio.ChannelAdapter;
import com.feng.io.nio.ChannelHandler;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NioServerHandler extends ChannelAdapter {

    public NioServerHandler(Selector selector, Charset charset) {
        super(selector, charset);
    }

    @Override
    public void channelActive(ChannelHandler channelHandler) {
        try {
            System.out.println("NioServer LocalAddress: " + channelHandler.channel().getLocalAddress());
            System.out.println("NioServer RemoteAddress: " + channelHandler.channel().getRemoteAddress());
            channelHandler.writeAndFlush("hi! this is NioServer, channel active \r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandler channelHandler, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        channelHandler.writeAndFlush(String.format("hi! this is NioServer, 我已经收到你的消息:%s. Success！\r\n", msg));
    }
}
