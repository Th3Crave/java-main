package com.x.microservice.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

//@Component
public class RedissonLock {

//    @Autowired
//    private Redisson redisson;

    @Autowired
    private RedissonClient redissonClient;

    public boolean lock(String key, long expireTime) {
        RLock lock = redissonClient.getLock(key);
        try {
            Boolean result = lock.tryLock(expireTime, TimeUnit.SECONDS);
            System.out.println("RedissonLock lock result:" + result);
            return result;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void unlock(String key) {
        RLock lock = redissonClient.getLock(key);
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
            System.out.println("RedissonLock unlock");
        }
    }
}
