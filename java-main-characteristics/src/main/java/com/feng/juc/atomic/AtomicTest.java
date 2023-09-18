package com.feng.juc.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicTest {

    private static AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>("1", 0);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("初始值：" + atomicStampedReference.getReference() + "，版本号：" + atomicStampedReference.getStamp());

        Thread thread1 = new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            atomicStampedReference.compareAndSet("1", "2", stamp, stamp + 1);
        });

        Thread thread2 = new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            atomicStampedReference.compareAndSet("2", "1", stamp, stamp + 1);
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("最终值：" + atomicStampedReference.getReference() + "，版本号：" + atomicStampedReference.getStamp());

    }
}
