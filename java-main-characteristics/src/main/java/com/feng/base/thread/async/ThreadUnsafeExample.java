package com.feng.base.thread.async;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUnsafeExample {
    private int cnt = 0;

    public void add() {
        cnt++;
    }

    private int get() {
        return cnt;
    }

    /**
     * synchronized 静态方法
     * 用ThreadUnsafeExample.class对象调用wait/notify方法
     */
    public static synchronized void test() {
        try {
            ThreadUnsafeExample.class.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ThreadUnsafeExample.class.notifyAll();
    }


    public static void main(String[] args) throws InterruptedException {
        final int threadSize = 20;
        ThreadUnsafeExample example = new ThreadUnsafeExample();
        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < threadSize; i++) {
            executorService.execute(() -> {
                example.add();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println(example.get());
    }
}
