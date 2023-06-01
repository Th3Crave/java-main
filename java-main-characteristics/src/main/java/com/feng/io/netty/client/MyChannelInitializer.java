package com.feng.io.netty.client;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
//        System.out.println("链接报告开始");
//        System.out.println("链接报告信息：本客户端链接到服务端。channelId：" + socketChannel.id());
//        System.out.println("链接报告完毕");

        // 基于换行符号
//        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));

        // 在管道中添加我们自己的接收数据实现方法
        // 消息出站处理器，在client发送消息时候会触发此处理器
        socketChannel.pipeline().addLast(new MyClientOutMsgHandler());
        // 消息入站处理器
        socketChannel.pipeline().addLast(new MyClientInMsgHandler());
    }
}
