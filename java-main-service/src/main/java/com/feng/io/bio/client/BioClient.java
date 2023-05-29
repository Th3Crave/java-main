package com.feng.io.bio.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class BioClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("10.192.198.3", 7397);
            System.out.println("this is BioClient, start!");
            BioClientHandler bioClientHandler = new BioClientHandler(socket, StandardCharsets.UTF_8);
            bioClientHandler.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
