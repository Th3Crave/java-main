package com.feng.io.aio.server;

import com.feng.io.aio.ChannelAdapter;
import com.feng.io.aio.ChannelHandler;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AioServerHandler extends ChannelAdapter {

    public AioServerHandler(AsynchronousSocketChannel channel, Charset charset) {
        super(channel, charset);
    }

    @Override
    public void channelActive(ChannelHandler channelHandler) {
        try {
            System.out.println("AioServer LocalAddress: " + channelHandler.channel().getLocalAddress());
            System.out.println("AioServer RemoteAddress: " + channelHandler.channel().getRemoteAddress());
            channelHandler.writeAndFlush("hi! this is AioServer, channel active \r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandler ctx) {
    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        ctx.writeAndFlush(String.format("hi! this is AioServer, 我已经收到你的消息:%s. Success！\r\n", msg));
    }
}
