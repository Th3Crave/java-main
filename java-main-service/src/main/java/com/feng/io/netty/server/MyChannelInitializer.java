package com.feng.io.netty.server;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;


public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
//        System.out.println("链接报告开始");
//        System.out.println("链接报告信息：有一客户端链接到本服务端");
//        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
//        System.out.println("链接报告Port:" + channel.localAddress().getPort());
//        System.out.println("链接报告完毕");
//
//        // 在管道中添加我们自己的接收数据实现方法
//        channel.pipeline().addLast(new MyServerHandler());

        /**
         * 解码器
         */
        // 1. 基于换行符号
        channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        // 2. 基于指定字符串【换行符，这样功能等同于LineBasedFrameDecoder】
        // e.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, false, Delimiters.lineDelimiter()));
        // 3. 基于最大长度
        // e.pipeline().addLast(new FixedLengthFrameDecoder(4));

        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
        // 编码转String，注意调整自己的编码格式GBK、UTF-8
        channel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyServerHandler());
    }
}
