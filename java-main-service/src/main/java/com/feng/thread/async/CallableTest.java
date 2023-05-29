package com.feng.thread.async;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CallableTest {

    public static void main(String[] args) {

        // FutureTask
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "callable test";
            }
        });

        new Thread(futureTask).start();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }


        // Future
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        // Executor.execute
        executorService.execute(() -> System.out.println("execute runnable"));
        // ExecutorService.submit(runnable/callable)
        Future<String> future = executorService.submit(() -> {
            System.out.println("submit runnable");
            return "future get";
        });
        try {
            // get时 阻塞当前线程
            System.out.println(future.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
