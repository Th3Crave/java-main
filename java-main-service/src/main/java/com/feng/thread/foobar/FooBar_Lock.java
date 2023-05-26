package com.feng.thread.foobar;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FooBar_Lock {

    private int n;
    public FooBar_Lock(int n) {
        this.n = n;
    }

    private ReentrantLock lock = new ReentrantLock();
    private final Condition condition1 = lock.newCondition();
    private final Condition condition2 = lock.newCondition();
    private final Condition condition3 = lock.newCondition();
    private int flag = 1;

    public void foo(Runnable printFoo) {
        for (int i = 0; i < n; i ++) {
            lock.lock();
            try {
                while (!(flag==1)) {
                    condition1.await();
                }
                printFoo.run();
                flag = 2;
                condition2.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }

    public void bar(Runnable printBar) {
        for (int i = 0; i < n; i ++) {
            lock.lock();
            try {
                while (!(flag==2)) {
                    condition2.await();
                }
                printBar.run();
                flag = 3;
                condition3.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }

        }
    }

    public void go(Runnable printGo) {
        for (int i = 0; i < n; i ++) {
            lock.lock();
            try {
                while (!(flag==3)) {
                    condition3.await();
                }
                printGo.run();
                flag = 1;
                condition1.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) {
        FooBar_Lock fooBarLock = new FooBar_Lock(5);
        new Thread(()-> fooBarLock.foo(()-> System.out.print("foo"))).start();
        new Thread(()-> fooBarLock.bar(()-> System.out.print("bar"))).start();
        new Thread(()-> fooBarLock.go(()-> System.out.println("go"))).start();
    }
}
