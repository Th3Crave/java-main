package com.x.microservice.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@Component
public class AsyncService {

    @Resource
    private Executor demoExecutor;

    @Async("demoExecutor")
    public Future<String> future() {
        return new AsyncResult<>("future");
    }


    public void testCompletableFuture() {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " completableFuture1");
            return "completableFuture1";
        }, demoExecutor);

//        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(()-> {
//            System.out.println(Thread.currentThread().getName() + " completableFuture2");
//            return "completableFuture2";
//            }, demoExecutor);
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " completableFuture2");
            return "completableFuture2";
        });

    }

}
