package com.feng.juc.locks.aqs.clh.spin;

import lombok.SneakyThrows;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 自旋锁
 */
public class SpinLock {

    private static Unsafe unsafe = null;
    private static final long valueOffset;
    private volatile int value = 0;

    static {
        try {
            unsafe = getUnsafeInstance();
            valueOffset = unsafe.objectFieldOffset(SpinLock.class.getDeclaredField("value"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private static Unsafe getUnsafeInstance() {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        return (Unsafe) theUnsafeInstance.get(Unsafe.class);
    }

    // 加锁
    public void lock() {
        System.out.println(Thread.currentThread().getName() + " spinlock try");

        for (; ; ) {
            int newV = value + 1;
            if (newV == 1 && (unsafe.compareAndSwapInt(this, valueOffset, 0, newV))) {
                return;
            }
        }
    }

    // 解锁
    public void unlock() {
        unsafe.compareAndSwapInt(this, valueOffset, 1, 0);
    }


    public static void main(String[] args) {
        final SpinLock spinLock = new SpinLock();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    spinLock.lock();
                    System.out.println(Thread.currentThread().getName() + " spinlock success");

                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                } finally {
                    spinLock.unlock();
                    System.out.println(Thread.currentThread().getName() + " unlock");
                }
            }).start();
        }
    }
}
