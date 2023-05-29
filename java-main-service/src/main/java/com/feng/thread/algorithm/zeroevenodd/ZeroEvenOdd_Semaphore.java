package com.feng.thread.algorithm.zeroevenodd;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class ZeroEvenOdd_Semaphore {

    private Semaphore zero = new Semaphore(1);
    private Semaphore even = new Semaphore(0);
    private Semaphore odd = new Semaphore(0);

    private int n;
    public ZeroEvenOdd_Semaphore(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i ++) {
            zero.acquire();
            printNumber.accept(0);
            if ((i & 1) == 1) {
                odd.release();
            } else {
                even.release();
            }
        }
    }

    // 偶数
    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i ++) {
            if ((i & 1) == 0) {
                even.acquire();
                printNumber.accept(i);
                zero.release();
            }
        }
    }

    // 奇数
    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i ++) {
            if ((i & 1) == 1) {
                odd.acquire();
                printNumber.accept(i);
                zero.release();
            }
        }
    }

    public static void main(String[] args) {
        ZeroEvenOdd_Semaphore zeroEvenOddSemaphore = new ZeroEvenOdd_Semaphore(6);
        new Thread(()->{
            try {
                zeroEvenOddSemaphore.zero(value -> System.out.print(value));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
                zeroEvenOddSemaphore.even(value -> System.out.print(value));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
                zeroEvenOddSemaphore.odd(value -> System.out.print(value));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
