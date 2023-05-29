package com.feng.thread.algorithm.zeroevenodd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;
import java.util.function.IntConsumer;

// LockSupport类的核心方法其实就两个：park()和unpark()，其中park()方法用来阻塞当前调用线程，unpark()方法用于唤醒指定线程。
// 这其实和Object类的wait()和signal()方法有些类似，但是LockSupport的这两种方法从语意上讲比Object类的方法更清晰，而且可以针对指定线程进行阻塞和唤醒。

// LockSupport类使用了一种名为Permit（许可）的概念来做到阻塞和唤醒线程的功能，可以把许可看成是一种(0,1)信号量（Semaphore），但与 Semaphore 不同的是，许可的累加上限是1。
// 初始时，permit为0，当调用unpark()方法时，线程的permit加1，当调用park()方法时，如果permit为0，则调用线程进入阻塞状态。
public class ZeroEvenOdd_LockSupport {

    private int n;

    public ZeroEvenOdd_LockSupport(int n) {
        this.n = n;
    }

    // 0-0 1-奇数 2-偶数
    volatile int state = 0;
    private Map<String, Thread> map = new ConcurrentHashMap<>();


    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        map.put("zero", Thread.currentThread());
        for (int i = 1; i <= n; i ++) {
            while (state != 0) {
                LockSupport.park();
            }
            printNumber.accept(0);
            if ((i % 2) == 0) {
                state = 2;
            } else {
                state = 1;
            }
            map.forEach((k, v) -> LockSupport.unpark(v));
        }
    }

    // 偶数
    public void even(IntConsumer printNumber) throws InterruptedException {
        map.put("even", Thread.currentThread());
        for (int i = 2; i <= n; i+=2) {
            while (state != 2) {
                LockSupport.park();
            }
            printNumber.accept(i);
            state = 0;
            LockSupport.unpark(map.get("zero"));
        }
    }

    // 奇数
    public void odd(IntConsumer printNumber) throws InterruptedException {
        map.put("odd", Thread.currentThread());
        for (int i = 1; i <= n; i+=2) {
            while (state != 1) {
                LockSupport.park();
            }
            printNumber.accept(i);
            state = 0;
            LockSupport.unpark(map.get("zero"));
        }
    }

    public static void main(String[] args) {
        ZeroEvenOdd_LockSupport zeroEvenOddLockSupport = new ZeroEvenOdd_LockSupport(10);
        new Thread(()->{
            try {
                zeroEvenOddLockSupport.zero(value -> System.out.print(value));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
                zeroEvenOddLockSupport.even(value -> System.out.print(value));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
                zeroEvenOddLockSupport.odd(value -> System.out.print(value));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
