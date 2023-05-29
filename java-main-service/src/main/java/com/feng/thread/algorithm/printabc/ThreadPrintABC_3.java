package com.feng.thread.algorithm.printabc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock Condition
 */
public class ThreadPrintABC_3 {

    static volatile int VALUE = 0;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();

        new Thread(()->{
            lock.lock();
            try {
                for (int i = 0; i < 10; i ++) {
                    while (VALUE % 3 != 0) {
                        conditionA.await();
                    }
                    System.out.println("A");
                    conditionB.signal();
                    VALUE ++;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "A").start();

        new Thread(()->{
            lock.lock();
            try {
                for (int i = 0; i < 10; i ++) {
                    while (VALUE % 3 != 1) {
                        conditionB.await();
                    }
                    System.out.println("B");
                    conditionC.signal();
                    VALUE ++;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "B").start();

        new Thread(()->{
            lock.lock();
            try {
                for (int i = 0; i < 10; i ++) {
                    while (VALUE % 3 != 2) {
                        conditionC.await();
                    }
                    System.out.println("C");
                    conditionA.signal();
                    VALUE ++;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "C").start();
    }
}
