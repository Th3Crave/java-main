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
                BioServerHandler bioServerHandler = new BioServerHandler(socket, Charset.forName("GBK"));
                bioServerHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
