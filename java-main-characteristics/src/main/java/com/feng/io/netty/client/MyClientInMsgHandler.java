package com.feng.io.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MyClientInMsgHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当客户端主动连接服务端，连接建立成功
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) {
        SocketChannel socketChannel = (SocketChannel) channelHandlerContext.channel();

        // 打印日志
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：本客户端链接到服务端。channelId：" + socketChannel.id());
        System.out.println("链接报告IP:" + socketChannel.localAddress().getHostString());
        System.out.println("链接报告Port:" + socketChannel.localAddress().getPort());
        System.out.println("链接报告完毕");

        //通知服务端链接建立成功
        String str = "通知服务端链接建立成功" + " " + new Date() + " " + socketChannel.localAddress().getHostString() + "\r\n";
        channelHandlerContext.writeAndFlush(str);
    }

    /**
     * 连接断开
     */
    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) {
        System.out.println("断开链接" + channelHandlerContext.channel().localAddress().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);

        // 通知服务端消息发送成功
        String str = new Date() + " 客户端client收到：" + msg + "\r\n";
        channelHandlerContext.writeAndFlush(str);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        channelHandlerContext.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }
}
