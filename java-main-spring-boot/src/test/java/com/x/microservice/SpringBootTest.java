package com.x.microservice;

import com.x.microservice.redis.RedisDistributedLock_lua;
import com.x.microservice.redis.RedissonLock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.boot.test.context.SpringBootTest
public class SpringBootTest {

    @Autowired
    private RedisDistributedLock_lua redisDistributedLockLua;
    @Autowired
    private RedissonLock redissonLock;

    @Test
    public void testLock() {
        redisDistributedLockLua.lock("test", "test", 10000);
    }

    @Test
    public void testUnlock() {
        redisDistributedLockLua.unlock("test", "test");
    }

    @Test
    public void testRedissonLock() {
        redissonLock.lock("test", 10000);
        redissonLock.unlock("test");
    }

    @Test
    public void testRedissonUnlock() {
        redissonLock.unlock("test");
    }
}
