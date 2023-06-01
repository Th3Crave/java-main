package com.feng.algorithm.thread.h2o;

import java.util.concurrent.Semaphore;

public class H2O_Semaphore {

    public H2O_Semaphore() {

    }

    private Semaphore h = new Semaphore(2);
    private Semaphore o = new Semaphore(0);

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        h.acquire(1);
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        o.release(1);
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        o.acquire(2);
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        h.release(2);
    }

    public static void main(String[] args) {
        H2O_Semaphore h2o = new H2O_Semaphore();
        String input = "OOHHHH";
        for (char c : input.toCharArray()) {
            if (c == 'H') {
                new Thread(() -> {
                    try {
                        h2o.hydrogen(() -> System.out.print(c));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            if (c == 'O')  {
                new Thread(() -> {
                    try {
                        h2o.oxygen(() -> System.out.print(c));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
}
