package com.feng.juc.locktools.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    public static void main(String[] args) {
        int permitsNum = 2;
        final Semaphore semaphore = new Semaphore(permitsNum);
        try {
            System.out.println("availablePermits:" + semaphore.availablePermits() + ",semaphore.tryAcquire(3,1, TimeUnit.SECONDS):" + semaphore.tryAcquire(3, 1, TimeUnit.SECONDS));
            semaphore.release();
            System.out.println("availablePermits:" + semaphore.availablePermits() + ",semaphore.tryAcquire(3,1, TimeUnit.SECONDS):" + semaphore.tryAcquire(3, 1, TimeUnit.SECONDS));
        } catch (Exception e) {

        }
    }
}
