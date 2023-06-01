package com.feng.juc.locks.aqs.clh;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * CLH锁
 */
public class CLHLock implements Lock {

    /**
     * Node节点内部类
     */
    static final class Node {
        volatile boolean locked;
    }

    // CLH队列尾节点
    private AtomicReference<Node> tail;
    // 前驱节点
    private ThreadLocal<Node> myPrev;
    // 当前节点
    private ThreadLocal<Node> myNode;

    public CLHLock() {
        // 初始化一个释放锁的节点，用作当前隐式队列的尾节点
        this.tail = new AtomicReference<>(new Node());
        this.myNode = ThreadLocal.withInitial(Node::new);
        this.myPrev = ThreadLocal.withInitial(() -> null);
    }

    /**
     * 加锁
     */
    @Override
    public void lock() {
        System.out.println(Thread.currentThread().getName() + " try lock");

        Node node = myNode.get();
        node.locked = true;

        // 将tail中缓存的node拿出来赋值给prev，将current放入tail中
        Node prev = tail.getAndSet(node);
        this.myPrev.set(prev);

        // 自旋判断上一个节点是否释放锁，直至获取到锁
        // locked == true  表示当前节点期望获取锁
        // locked == false 表示当前节点已经释放锁
        // prev.locked == true  表示上一个节点未释放锁
        // prev.locked == false 表示上一个节点释放锁了, 也就意味着自己竞争到锁了
        while (prev.locked) {
        }

        System.out.println(Thread.currentThread().getName() + " get lock");
    }

    /**
     * 解锁
     */
    @Override
    public void unlock() {
        Node node = myNode.get();
        node.locked = false;
        System.out.println(Thread.currentThread().getName() + " unlock");
        this.myNode.remove();
        this.myPrev.remove();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }


    public static void main(String[] args) {
        final Lock lock = new CLHLock();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    lock.lock();
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                } finally {
                    lock.unlock();
                }
            }).start();
        }
    }
}
