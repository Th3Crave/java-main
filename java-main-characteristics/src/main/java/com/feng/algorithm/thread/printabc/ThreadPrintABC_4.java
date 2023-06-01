package com.feng.algorithm.thread.printabc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock Condition CountDownLatch
 * ABC
 * ABC
 * END
 */
public class ThreadPrintABC_4 extends Thread {

    private static CountDownLatch countDownLatch = new CountDownLatch(3);

    private static ReentrantLock LOCK = new ReentrantLock();
    private static int COUNT = 10;

    private Condition cur;
    private Condition next;
    private char aChar;
    public ThreadPrintABC_4(Condition cur, Condition next, char aChar) {
        this.cur = cur;
        this.next = next;
        this.aChar = aChar;
    }

    @Override
    public void run() {
        LOCK.lock();
        try {
            for (int i = 1; i <= COUNT; i ++) {
                System.out.print(aChar);
                if (aChar == 'C') {
                    System.out.println();
                }
                next.signal();
                if (i < COUNT) {
                    try {
                        cur.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } finally {
            LOCK.unlock();
        }
        countDownLatch.countDown();
//        System.out.println(aChar + " end at " + System.currentTimeMillis());
    }

    public static void main(String[] args) {
        Condition a = LOCK.newCondition();
        Condition b = LOCK.newCondition();
        Condition c = LOCK.newCondition();

        new ThreadPrintABC_4(a, b, 'A').start();
        new ThreadPrintABC_4(b, c, 'B').start();
        new ThreadPrintABC_4(c, a, 'C').start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("END");
    }
}


