package com.feng.base.thread.threadlocal;

public class Test {
    private static final ThreadLocal<String> threadlocal = new ThreadLocal<>();
    private static final InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadlocal.set("thread-local");
        inheritableThreadLocal.set("inheritableThreadLocal");

        System.out.println(threadlocal.get());
        System.out.println(inheritableThreadLocal.get());

        new Thread(() -> {
            System.out.println(threadlocal.get());
            System.out.println(inheritableThreadLocal.get());
        }).start();
    }
}
