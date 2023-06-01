package com.feng.juc.locktools.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {


    private static class TravelTask implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private String name;
        private int arriveTime;

        public TravelTask(CyclicBarrier cyclicBarrier, String name, int arriveTime) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
            this.arriveTime = arriveTime;
        }

        @Override
        public void run() {
            try {
                // 模拟到达需要的时间
                Thread.sleep(arriveTime * 1000);
                System.out.println(name + "到达集合点。");
                cyclicBarrier.await();
                System.out.println(name + "可以出发了！");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }

        }
    }


    public static void main(String[] args) throws InterruptedException {
        int num = 3;

        CyclicBarrier cyclicBarrier = new CyclicBarrier(num, ()->{
            System.out.println(Thread.currentThread().getName() + ":*******导游开始分发护照****");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ":*******导游分发护照完毕****");
        });

//        CyclicBarrier cyclicBarrier = new CyclicBarrier(num);

//        Executor executor = Executors.newFixedThreadPool(num);
//        executor.execute(new TravelTask(cyclicBarrier,"哈登",5));
//        executor.execute(new TravelTask(cyclicBarrier,"杜兰特",3));
//        executor.execute(new TravelTask(cyclicBarrier,"欧文",1));

        new Thread(new TravelTask(cyclicBarrier,"哈登",2), "哈登").start();
        new Thread(new TravelTask(cyclicBarrier,"杜兰特",2), "杜兰特").start();
        new Thread(new TravelTask(cyclicBarrier,"欧文",2), "欧文").start();

        Thread.sleep(4000);

        new Thread(new TravelTask(cyclicBarrier,"1",2), "1").start();
        new Thread(new TravelTask(cyclicBarrier,"2",2), "2").start();
        new Thread(new TravelTask(cyclicBarrier,"3",3), "3").start();



    }


}
