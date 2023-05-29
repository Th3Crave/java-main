package com.feng.thread.algorithm.printabc;

/**
 * 抢占式
 */
public class ThreadPrintABC_2 {

    public static void main(String[] args) {

        new Thread(new PrintTask(0)).start();
        new Thread(new PrintTask(1)).start();
        new Thread(new PrintTask(2)).start();
    }

    private static class PrintTask implements Runnable {

        private static Object LOCK = new Object();
        private static final int PRINT_NUM = 3;
        private static int CURRENT = 0;

        private int index;
        public PrintTask(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            while (CURRENT < PRINT_NUM) {
                synchronized (LOCK) {
                    if (CURRENT < PRINT_NUM && CURRENT % 3 == index) {
//                    if (CURRENT % 3 == index) {
                        System.out.println(CURRENT + " " + (char) ('A' + CURRENT % 3));
                        CURRENT ++;
                    }
                }
            }
        }
    }

}
