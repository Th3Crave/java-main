package com.feng.algorithm.thread.foobar;

import java.util.concurrent.Semaphore;

public class FooBar_Semaphore {

    private int n;
    public FooBar_Semaphore(int n) {
        this.n = n;
    }

    private Semaphore foo = new Semaphore(1);
    private Semaphore bar = new Semaphore(0);
    private Semaphore go = new Semaphore(0);


    public void foo(Runnable printFoo) {
        for (int i = 0; i < n; i ++) {
            try {
                foo.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            printFoo.run();
            bar.release();
        }
    }

    public void bar(Runnable printBar) {
        for (int i = 0; i < n; i ++) {
            try {
                bar.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            printBar.run();
            go.release();
        }
    }

    public void go(Runnable printGo) {
        for (int i = 0; i < n; i ++) {
            try {
                go.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            printGo.run();
            foo.release();
        }
    }


    public static void main(String[] args) {
        FooBar_Semaphore fooBarSemaphore = new FooBar_Semaphore(3);
        new Thread(()-> fooBarSemaphore.foo(()-> System.out.print("foo"))).start();
        new Thread(()-> fooBarSemaphore.bar(()-> System.out.print("bar"))).start();
        new Thread(()-> fooBarSemaphore.go(()-> System.out.print("go"))).start();
    }
}
