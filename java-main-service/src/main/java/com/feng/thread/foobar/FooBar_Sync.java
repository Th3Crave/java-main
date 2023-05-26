package com.feng.thread.foobar;

public class FooBar_Sync {

    private int n;
    public FooBar_Sync(int n) {
        this.n = n;
    }

    private final Object lock = new Object();
    private volatile int flag = 1;

    public void foo(Runnable printFoo) {
        for (int i = 0; i < n; i ++) {
            synchronized (lock) {
                while (!(flag == 1)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                printFoo.run();
                flag = 2;
                lock.notifyAll();
            }

        }
    }

    public void bar(Runnable printBar) {
        for (int i = 0; i < n; i ++) {
            synchronized (lock) {
                while (!(flag==2)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                printBar.run();
                flag = 3;
                lock.notifyAll();
            }
        }
    }

    public void go(Runnable printGo) {
        for (int i = 0; i < n; i ++) {
            synchronized (lock) {
                while (!(flag==3)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                printGo.run();
                flag = 1;
                lock.notifyAll();
            }
        }
    }


    public static void main(String[] args) {
        FooBar_Sync fooBarObject = new FooBar_Sync(5);
        new Thread(()-> fooBarObject.foo(()-> System.out.print("foo"))).start();
        new Thread(()-> fooBarObject.bar(()-> System.out.print("bar"))).start();
        new Thread(()-> fooBarObject.go(()-> System.out.println("go"))).start();

    }
}
