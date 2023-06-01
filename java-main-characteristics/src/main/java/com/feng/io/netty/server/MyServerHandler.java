package com.feng.io.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当客户端主动连接服务端后，这个通道就是活跃的了。
     * 也就是客户端与服务端建立了通信通道并且可以传输数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel channel = (SocketChannel) ctx.channel();
        // 日志信息
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端");
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        System.out.println("链接报告完毕");

//        // 当有客户端连接后，添加到channelGroup通信组
//        ChannelHandler.channelGroup.add(ctx.channel());

        // 通知客户端连接建立成功
        // 在windows里，\r\n表示回车换行
        String str = "通知客户端连接建立成功 " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
//        ByteBuf buf = Unpooled.buffer(str.getBytes().length);
//        buf.writeBytes(str.getBytes("GBK"));
//        ctx.writeAndFlush(buf);
        // 在MyChannelInitializer#initChannel添加了编码器StringEncoder 可以直接发送String
//        ctx.writeAndFlush(str);
    }

    /**
     * 当客户端主动断开与服务端的连接后，这个通道就是不活跃的。
     * 也就是说客户端与服务端关闭了通信通道并且不可以传输数据
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端断开链接 " + ctx.channel().localAddress().toString());

//        // 当有客户端退出后 从channelGroup中移除
//        ChannelHandler.channelGroup.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        // 接收msg消息
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] msgByte = new byte[buf.readableBytes()];
//        buf.readBytes(msgByte);
////        System.out.print(new Date() + "接收到消息：");
////        System.out.println(new String(msgByte, Charset.forName("GBK")));
//        System.out.println(new Date() + "接收到消息：" + new String(msgByte, Charset.forName("GBK")));

        // 接收msg消息 此处已不需要自己进行解码 在MyChannelInitializer#initChannel添加了解码器StringDecoder
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 服务端server接收到消息：" + msg);

        // 通知客户端消息发送成功
        String str = new Date() + " 服务端server收到：" + msg + "\r\n";
//        ByteBuf buf = Unpooled.buffer(str.getBytes().length);
//        buf.writeBytes(str.getBytes("GBK"));
//        ctx.writeAndFlush(buf);
        // 在MyChannelInitializer#initChannel添加了编码器StringEncoder 可以直接发送String
//        ctx.writeAndFlush(str);

//        // 收到消息后 群发给客户端
//        ChannelHandler.channelGroup.writeAndFlush(str);
    }


    /**
     * 抓住异常
     * 当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭连接
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }
}
