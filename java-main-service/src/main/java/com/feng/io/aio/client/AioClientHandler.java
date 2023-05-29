package com.feng.io.aio.client;


import com.feng.io.aio.ChannelAdapter;
import com.feng.io.aio.ChannelHandler;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AioClientHandler extends ChannelAdapter {

    public AioClientHandler(AsynchronousSocketChannel channel, Charset charset) {
        super(channel, charset);
    }

    @Override
    public void channelActive(ChannelHandler channelHandler) {
        try {
            System.out.println("AioClient LocalAddress: " + channelHandler.channel().getLocalAddress());
            System.out.println("AioClient RemoteAddress: " + channelHandler.channel().getRemoteAddress());
            channelHandler.writeAndFlush("hi! this is AioClient, channel active \r\n");
            //通知服务端链接建立成功
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandler ctx) {
    }

    @Override
    public void channelRead(ChannelHandler channelHandler, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        channelHandler.writeAndFlush(String.format("hi! this is AioClient, 我已经收到你的消息:%s. Success！\r\n", msg));
    }

}
