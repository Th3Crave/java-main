package com.feng.thread.fizzbuzz;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class FizzBuzz_Semaphore {
    private int n;

    public FizzBuzz_Semaphore(int n) {
        this.n = n;
    }

    Semaphore number = new Semaphore(1);
    Semaphore fizz = new Semaphore(0);
    Semaphore buzz = new Semaphore(0);
    Semaphore fizzbuzz = new Semaphore(0);

    // % 3 == 0
    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 1; i <= n; i ++) {
            if (i % 3 == 0 && i % 5 != 0) {
                fizz.acquire();
                printFizz.run();
                number.release();
            }
        }
    }

    // % 5 == 0
    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 1; i <= n; i ++) {
            if (i % 5 == 0 && i % 3 != 0) {
                buzz.acquire();
                printBuzz.run();
                number.release();
            }
        }
    }

    // % 3 == 0 &&  % 5 == 0
    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 1; i <= n; i ++) {
            if (i % 3 == 0 && i % 5 == 0) {
                fizzbuzz.acquire();
                printFizzBuzz.run();
                number.release();
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i ++) {
            number.acquire();
            if (i % 3 == 0 && i % 5 == 0) {
                fizzbuzz.release();
            } else if (i % 3 == 0) {
                fizz.release();
            } else if (i % 5 == 0) {
                buzz.release();
            } else {
                printNumber.accept(i);
                number.release();
            }
        }
    }


    public static void main(String[] args) {
        FizzBuzz_Semaphore fizzBuzzSemaphore = new FizzBuzz_Semaphore(15);
        new Thread(()->{
            try {
                fizzBuzzSemaphore.fizz(()->{
                    System.out.print("fizz, ");
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
                fizzBuzzSemaphore.buzz(()->{
                    System.out.print("buzz, ");
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
                fizzBuzzSemaphore.fizzbuzz(()->{
                    System.out.print("fizzbuzz, ");
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
                fizzBuzzSemaphore.number(value -> System.out.print(value + ", "));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
