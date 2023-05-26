package com.feng.thread.printabc;

/**
 * 协作式
 */
public class ThreadPrintABC_1 {

    public static void main(String[] args) {
        new Thread(new PrintTask(0,'A')).start();
        new Thread(new PrintTask(1,'B')).start();
        new Thread(new PrintTask(2,'C')).start();
    }

    static class PrintTask implements Runnable {
        // 锁对象
        private static Object LOCK = new Object();
        // 打印控制变量
        private static int FLAG = 0;
        // 成员变量 每个线程打印次数
        private int num = 100;

        private int sort;
        private char name;
        public PrintTask(int sort, char name) {
            this.sort = sort;
            this.name = name;
        }

        @Override
        public void run() {
            synchronized (LOCK) {
                while (num > 0) {
                    if (FLAG % 3 == sort) {
//                        System.out.println(name);
                        System.out.println((char) ('A' + FLAG % 3));
                        FLAG++;
                        num --;
                        LOCK.notifyAll();
                    } else {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }
}
