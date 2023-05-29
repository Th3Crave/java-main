package com.feng.thread.algorithm.printabc;

import java.util.concurrent.Semaphore;

public class PrintABC_Semaphore {

    private int num;
    public PrintABC_Semaphore (int num) {
        this.num = num;
    }

    private Semaphore semaphoreA = new Semaphore(1);
    private Semaphore semaphoreB = new Semaphore(0);
    private Semaphore semaphoreC = new Semaphore(0);

    public void printA(Runnable printA) throws InterruptedException {
        for (int i = 0; i < num; i++) {
            semaphoreA.acquire();
            printA.run();
            semaphoreB.release();
        }
    }

//    public void printB(Runnable printB) throws InterruptedException {
    public void printB() throws InterruptedException {
        for (int i = 0; i < num; i++) {
            semaphoreB.acquire();
            System.out.print("B");
            semaphoreC.release();
        }
    }

    public void printC(Runnable printC) throws InterruptedException {
        for (int i = 0; i < num; i++) {
            semaphoreC.acquire();
            printC.run();
            semaphoreA.release();
        }
    }

    public static void main(String[] args) {
        PrintABC_Semaphore printABCSemaphore = new PrintABC_Semaphore(10);
        new Thread(()->{
            try {
                printABCSemaphore.printA(()-> System.out.print("A"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
//                printABCSemaphore.printB(()-> System.out.print("B"));
                printABCSemaphore.printB();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
                printABCSemaphore.printC(()-> System.out.println("C"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


}
