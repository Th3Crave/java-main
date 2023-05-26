package com.feng.juc.locksupport;

import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {


    static class MyThread extends Thread {
        private Object object;

        public MyThread(Object object) {
            this.object = object;
        }

        public void run() {
            System.out.println("before unpark, " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 获取blocker
            System.out.println("Blocker info " + LockSupport.getBlocker((Thread) object));
            // 释放许可
            LockSupport.unpark((Thread) object);
            // 休眠500ms，保证先执行park中的setBlocker(t, null);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 再次获取blocker
            System.out.println("Blocker info " + LockSupport.getBlocker((Thread) object));

            System.out.println("after unpark, " + Thread.currentThread().getName());
        }
    }


    public static void main(String[] args) {
//        MyThread myThread = new MyThread(Thread.currentThread());
//        myThread.start();
//        System.out.println("before park, " + Thread.currentThread().getName());
//        // 获取许可
//        LockSupport.park("ParkAndUnparkDemo");
//        System.out.println("after park, " + Thread.currentThread().getName());

        Thread thread = new Thread(() -> {
            LockSupport.unpark(Thread.currentThread());
            LockSupport.park();
            System.out.println("test lockSupport.part");
        }, "t1");

        thread.start();
//        LockSupport.unpark(thread);
        System.out.println("main");

    }
}