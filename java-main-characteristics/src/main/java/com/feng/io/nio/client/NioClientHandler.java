package com.feng.io.nio.client;

import com.feng.io.nio.ChannelAdapter;
import com.feng.io.nio.ChannelHandler;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NioClientHandler extends ChannelAdapter {

    public NioClientHandler(Selector selector, Charset charset) {
        super(selector, charset);
    }

    @Override
    public void channelActive(ChannelHandler channelHandler) {
        try {
            System.out.println("NioClient LocalAddress: " + channelHandler.channel().getLocalAddress());
            System.out.println("NioClient RemoteAddress: " + channelHandler.channel().getRemoteAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
        channelHandler.writeAndFlush("hi! this is NioClient, channel active \r\n");
    }

    @Override
    public void channelRead(ChannelHandler channelHandler, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        channelHandler.writeAndFlush(String.format("hi! this is NioClient, 我已经收到你的消息:%s. Success！\r\n", msg));
    }
}
