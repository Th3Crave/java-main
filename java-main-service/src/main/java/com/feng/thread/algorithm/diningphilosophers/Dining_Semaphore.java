package com.feng.thread.algorithm.diningphilosophers;

import java.util.concurrent.Semaphore;

public class Dining_Semaphore {

    int num = 5;

    private Semaphore[] semaphores = new Semaphore[]{
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1)
    };

    public Dining_Semaphore() {

    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        // 左手筷子位置
        int left = philosopher;
        // 右手筷子位置
        int right = (philosopher + 1) % num;

        while (true) {
            // 左手拿到筷子
            if (semaphores[left].tryAcquire()) {
                // 左手拿到筷子 && 右手拿到筷子
                if (semaphores[right].tryAcquire()) {
                    pickLeftFork.run();
                    pickRightFork.run();
                    eat.run();
                    putLeftFork.run();
                    semaphores[left].release();
                    putRightFork.run();
                    semaphores[right].release();
                    // 吃完饭，退出循环
                    break;
                } else {
                    // 左手拿到筷子 && 右手没拿到筷子
                    semaphores[left].release();
                    Thread.yield();
                }
            } else {
                // 左手没拿到筷子，先思考会吧
                Thread.yield();
            }
        }
    }
}
