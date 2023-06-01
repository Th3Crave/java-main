package com.feng.juc.executor.forkjoin;

import lombok.SneakyThrows;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinMain {

    @SneakyThrows
    public static void main(String[] args) {
//        /**
//         * 采用Fork/Join异步计算1+2+3+...+10000
//         */
//        ForkJoinPool pool = new ForkJoinPool();
//        ForkJoinTask<Integer> task = new SumAccumulationTask(1, 10000);
//        pool.submit(task);
//        System.out.println(task.get());

        /**
         * 斐波那契数列
         * F(1)=1
         * F(2)=1
         * F(n)=F(n-1)+F(n-2)（n>=3，n∈N*）
         */
        ForkJoinPool forkJoinPool = new ForkJoinPool(4); // 最大并发数4
        int n = 9;
        Fibonacci fibonacci = new Fibonacci(n);
        long start = System.currentTimeMillis();
        Integer result = forkJoinPool.invoke(fibonacci);
        long end = System.currentTimeMillis();
        System.out.println("Fork/join fibonacci-" + n + ", sum:" + result + " in " + (end - start) + " ms.");
    }

    /**
     * 采用Fork/Join异步计算1+2+3+...+10000
     */
    static final class SumAccumulationTask extends RecursiveTask<Integer> {

        // 开始计算的数
        final int start;
        // 最后计算的数
        final int end;

        SumAccumulationTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            // 如果计算量小于1000，那么分配一个线程执行if中的代码块，并返回执行结果
            if (end - start < 1000) {
                System.out.println(Thread.currentThread().getName() + " 开始执行：" + start + "-" + end);

                int sum = 0;
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
                return sum;
            }

            // 如果计算量大于1000，那么拆分为两个任务
            SumAccumulationTask task1 = new SumAccumulationTask(start, (start + end) / 2);
            SumAccumulationTask task2 = new SumAccumulationTask((start + end) / 2 + 1, end);
            // 执行任务
            task1.fork();
            task2.fork();
            // 获取任务执行的结果
            return task1.join() + task2.join();
        }
    }


    /**
     * 斐波那契数列
     */
    static class Fibonacci extends RecursiveTask<Integer> {
        final int n;

        Fibonacci(int anInt) {
            this.n = anInt;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            // 创建子任务
            f1.fork();

            Fibonacci f2 = new Fibonacci(n - 2);
            // 等待子任务结果，并合并结果
            return f2.compute() + f1.join();
        }
    }
}


