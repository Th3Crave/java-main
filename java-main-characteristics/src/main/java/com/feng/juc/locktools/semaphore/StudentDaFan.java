package com.feng.juc.locktools.semaphore;

import java.util.concurrent.Semaphore;

public class StudentDaFan {


    public static class Student extends Thread {
        private Semaphore semaphore;
        private String name;

        private Student(Semaphore semaphore, String name) {
            this.semaphore = semaphore;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(name + "正在打饭...." );
                Thread.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println(name + "打完饭了！");
                semaphore.release();
            }
        }

    }

    public static void main(String[] args) {
        // 3个窗口
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <= 10; i ++) {
            new Student(semaphore, "Student" + i).start();
        }
    }
}
