package com.feng.juc.locks.reentrantlock;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    @SneakyThrows
    public static void main(String[] args) {
//        LockDemoRunnable lockDemoRunnable = new LockDemoRunnable();
//        new Thread(lockDemoRunnable, "thread1").start();
//        new Thread(lockDemoRunnable, "thread2").start();


//        // 循环打印ABC PrintABC_Runnable
//        Condition a = PrintABC_Runnable.LOCK.newCondition();
//        Condition b = PrintABC_Runnable.LOCK.newCondition();
//        Condition c = PrintABC_Runnable.LOCK.newCondition();
//
//        new Thread(new PrintABC_Runnable(a, b, 'A')).start();
//        Thread.sleep(1000);
//        new Thread(new PrintABC_Runnable(b, c, 'B')).start();
//        Thread.sleep(1000);
//        new Thread(new PrintABC_Runnable(c, a, 'C')).start();


        // 循环打印ABC PrintABC_Thread
        Condition ta = PrintABC_Thread.LOCK.newCondition();
        Condition tb = PrintABC_Thread.LOCK.newCondition();
        Condition tc = PrintABC_Thread.LOCK.newCondition();
        new PrintABC_Thread(ta, tb, 'A').start();
        new PrintABC_Thread(tb, tc, 'B').start();
        new PrintABC_Thread(tc, ta, 'C').start();
    }


    /**
     * 占用锁1秒然后释放
     */
    static class LockDemoRunnable implements Runnable {
        // 公平锁 保证当前线程释放锁后一定是等待队列里的线程获取锁
        private static ReentrantLock lock = new ReentrantLock(true);

        @Override
        public void run() {
            while (true) {
                try {
                    // acquire lock
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " acquire a lock success");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // release lock
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + " release the lock");
                }
            }
        }
    }


    static class PrintABC_Runnable implements Runnable {

        private static ReentrantLock LOCK = new ReentrantLock();
        private static int COUNT = 5;

        private Condition current;
        private Condition next;
        private char print;

        public PrintABC_Runnable(Condition current, Condition next, char print) {
            this.current = current;
            this.next = next;
            this.print = print;
        }

        @Override
        public void run() {
            LOCK.lock();
            try {
                for (int i = 1; i <= COUNT; i++) {
                    System.out.println(this.print);
                    this.next.signal();
                    if (i < COUNT) {
                        this.current.await();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                LOCK.unlock();
            }
        }
    }


    static class PrintABC_Thread extends Thread {

        private static ReentrantLock LOCK = new ReentrantLock();
        private static int COUNT = 5;

        private Condition current;
        private Condition next;
        private char print;

        public PrintABC_Thread(Condition current, Condition next, char print) {
            this.current = current;
            this.next = next;
            this.print = print;
        }

        @Override
        public void run() {
            LOCK.lock();
            try {
                for (int i = 1; i <= COUNT; i++) {
                    System.out.println(this.print);
                    this.next.signal();
                    if (i < COUNT) {
                        try {
                            this.current.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } finally {
                LOCK.unlock();
            }
        }
    }

}
