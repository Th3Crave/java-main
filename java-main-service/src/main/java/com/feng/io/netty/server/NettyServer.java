package com.feng.io.netty.server;


// https://github.com/fuzhengwei/itstack-demo-netty.git
// https://bugstack.cn/md/netty/base/2019-08-01-netty%E6%A1%88%E4%BE%8B%EF%BC%8Cnetty4.1%E5%9F%BA%E7%A1%80%E5%85%A5%E9%97%A8%E7%AF%87%E4%B8%80%E3%80%8A%E5%97%A8%EF%BC%81NettyServer%E3%80%8B.html
// NetAssist 网络调试助手

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().bing(7397);
    }

    private void bing(int port) {
        // 配置服务端NIO线程组
        //NioEventLoopGroup extends MultithreadEventLoopGroup Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));

        // EventLoopGroup事件循环组
        //  NioEventLoopGroup异步事件循环组
        //  MultithreadEventLoopGroup多线程事件循环组等。
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup) // parentGroup主要用于接收请求链接，链接成功后交给childGroup处理收发数据等事件
                    .channel(NioServerSocketChannel.class) // 非阻塞模式
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer());

            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("My-NettyServer start!");
            System.out.println("My-NettyServer localAddress " + future.channel().localAddress());
            System.out.println("My-NettyServer remoteAddress " + future.channel().remoteAddress());

            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }
    }
}
