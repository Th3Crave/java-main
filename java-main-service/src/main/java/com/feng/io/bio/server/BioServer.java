package com.feng.io.bio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class BioServer extends Thread {

    private ServerSocket serverSocket = null;

    public static void main(String[] args) {
        BioServer bioServer = new BioServer();
        bioServer.start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(7397));
            System.out.println("this is BioServer, start! " + this.serverSocket.getInetAddress());
            while (true) {
                Socket socket = serverSocket.accept();
                // BIO时 服务端处理客户端连接的时候 一个客户端连接相应的需要一个线程处理
                BioServerHandler bioServerHandler = new BioServerHandler(socket, Charset.forName("GBK"));
                bioServerHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
