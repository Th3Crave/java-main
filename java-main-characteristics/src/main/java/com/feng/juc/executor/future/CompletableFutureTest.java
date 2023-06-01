package com.feng.juc.executor.future;

import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;

/**
 * Future的局限性：它没法直接对多个任务进行链式、组合等处理，需要借助并发工具类才能完成，实现逻辑比较复杂。
 * <p>
 * CompletableFuture是对Future的扩展和增强
 * CompletableFuture实现了对任务编排的能力。借助这项能力，可以轻松地组织不同任务的运行顺序、规则以及方式。从某种程度上说，这项能力是它的核心能力
 * CompletableFuture 内部默认使用了ForkJoinPool来处理异步任务，如果在某些业务场景我们想自定义自己的异步线程池也是可以的。
 */
public class CompletableFutureTest {

    @SneakyThrows
    public static void main(String[] args) {
        // 异步执行
//        async();


        /**
         * 依赖关系
         * thenApply()：把前面任务的执行结果，交给后面的Function
         * thenCompose()：用来连接两个有依赖关系的任务，结果由第二个任务返回
         */
        CompletableFuture<Integer> completableFuture_dependence_1 = CompletableFuture.supplyAsync(() -> 1);
        CompletableFuture<Integer> completableFuture_dependence_2 = completableFuture_dependence_1.thenApply(data -> data + 1);
        System.out.println(completableFuture_dependence_2.get());

        /**
         * and集合关系
         * thenCombine()：合并任务，有返回值
         * thenAccepetBoth()：两个任务执行完成后，将结果交给thenAccepetBoth处理，无返回值
         * runAfterBoth()：两个任务都执行完成后，执行下一步操作(Runnable类型任务)
         */

        /**
         * or聚合关系
         * applyToEither()：两个任务哪个执行的快，就使用哪一个结果，有返回值
         * acceptEither()：两个任务哪个执行的快，就消费哪一个结果，无返回值
         * runAfterEither()：任意一个任务执行完成，进行下一步操作(Runnable类型任务)
         */


        /**
         * 并行执行
         * allOf()：当所有给定的 CompletableFuture 完成时，返回一个新的 CompletableFuture
         * anyOf()：当任何一个给定的CompletablFuture完成时，返回一个新的CompletableFuture
         */


        /**
         * 结果处理
         * whenComplete：当任务完成时，将使用结果(或 null)和此阶段的异常(或 null如果没有)执行给定操作
         * exceptionally：返回一个新的CompletableFuture，当前面的CompletableFuture完成时，它也完成，当它异常完成时，给定函数的异常触发这个CompletableFuture的完成
         */
    }

    @SneakyThrows
    public static void async() {
        /**
         * CompletableFuture提供了四个静态方法来创建一个异步操作：
         *
         * public static CompletableFuture<Void> runAsync(Runnable runnable)
         * public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
         * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
         * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
         */
        CompletableFuture<Integer> completableFuture_1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ": completableFuture_1 do something");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        });

        CompletableFuture<Void> completableFuture_2 = completableFuture_1.thenRunAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ": completableFuture_2 do something");
        });

        // 等待任务1执行完成
        System.out.println("completableFuture_1 -> " + completableFuture_1.get());
        // 等待任务2执行完成
        System.out.println("completableFuture_2 -> " + completableFuture_2.get());
    }
}
