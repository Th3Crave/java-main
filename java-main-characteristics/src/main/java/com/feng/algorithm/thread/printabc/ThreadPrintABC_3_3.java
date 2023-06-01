package com.feng.algorithm.thread.printabc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPrintABC_3_3 extends Thread {

    private static ReentrantLock LOCK = new ReentrantLock();

    private Condition cur;
    private Condition next;
    private char aChar;
    private ThreadPrintABC_3_3(Condition cur, Condition next, char aChar) {
        this.cur = cur;
        this.next = next;
        this.aChar = aChar;
    }

    // 循环次数
    private static int COUNT = 10;

    @Override
    public void run() {
        LOCK.lock();
        try {
            for (int i = 1; i <= COUNT; i ++) {
                System.out.println(aChar);
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
    }

    public static void main(String[] args) {
        Condition a = LOCK.newCondition();
        Condition b = LOCK.newCondition();
        Condition c = LOCK.newCondition();

        new ThreadPrintABC_3_3(a, b, 'A').start();
        new ThreadPrintABC_3_3(b, c, 'B').start();
        new ThreadPrintABC_3_3(c, a, 'C').start();
    }
}
