package com.feng.algorithm.thread;

public class ThreadTest {

    static int num = 0;

    private static volatile int x = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                for (int y = 0; y < 100; y++) {
                    num++;
                    x++;
                }
            }).start();
        }
        System.out.println(num);
        System.out.println(x);
    }
}
