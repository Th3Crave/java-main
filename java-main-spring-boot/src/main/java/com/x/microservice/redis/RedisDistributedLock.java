package com.x.microservice.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

//@Component
public class RedisDistributedLock {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean lock(String key, String value, long expireTime) {
        /**
         * setIfAbsent 相当于Redis的SETNX命令，是一个原子操作
         */
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.MILLISECONDS);
        return success != null && success;
    }

    public void unlock(String key, String value) {
        String currentValue = redisTemplate.opsForValue().get(key);
        if (currentValue != null && currentValue.equals(value)) {
            redisTemplate.delete(key);
        }
        /**
         * 这种释放锁的方式存在线程安全问题。
         * 当多个线程同时争抢同一个 Redis 锁时，如果这些线程都是通过使用 RedisTemplate 的 delete() 方法来释放锁的话，可能会出现以下情况：
         * 1）线程 A 成功获取了锁，并设置了一个过期时间；
         * 2）过了一段时间后，锁的过期时间到了，Redis 自动将锁删除；
         * 3）同时，线程 B 也在尝试获取锁，由于此时锁已经被 Redis 删除了，线程 B 成功获取了锁；
         * 4）线程 A 在这个时候调用了 RedisTemplate 的 delete() 方法来释放锁，由于此时 Redis 中已经不存在该锁了，所以线程 A 的操作实际上是删除了线程 B 获取到的锁，从而导致线程 B 的锁失效。
         */
    }
}
