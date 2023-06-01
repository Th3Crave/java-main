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
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        /**
         * 每个 Boss NioEventLoop 循环执行的任务包含 3 步：
         * 1）轮询 Accept 事件；
         * 2）处理 Accept I/O 事件，与 Client 建立连接，生成 NioSocketChannel，并将 NioSocketChannel 注册到某个 Worker NioEventLoop 的 Selector 上；
         * 3）处理任务队列中的任务，runAllTasks。任务队列中的任务包括用户调用 eventloop.execute 或 schedule 执行的任务，或者其他线程提交到该 eventloop 的任务。
         *
         * 每个 Worker NioEventLoop 循环执行的任务包含 3 步：
         * 1）轮询 Read、Write 事件；
         * 2）处理 I/O 事件，即 Read、Write 事件，在 NioSocketChannel 可读、可写事件发生时进行处理；
         * 3）处理任务队列中的任务，runAllTasks。
         */

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup) // 主从Reactor多线程模型 bossGroup主要用于接收请求链接，链接成功后交给workerGroup处理收发数据等事件
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
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
