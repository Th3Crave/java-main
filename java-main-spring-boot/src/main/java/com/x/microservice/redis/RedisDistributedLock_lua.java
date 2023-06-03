package com.x.microservice.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Arrays;

/**
 * redis分布式锁
 */
//@Component
public class RedisDistributedLock_lua {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * LOCK_SCRIPT 和 UNLOCK_SCRIPT 分别是获取锁和释放锁的 Lua 脚本
     * 其中 KEYS[1] 表示 Redis 键名，ARGV[1] 表示 Redis 键值，ARGV[2] 表示锁的过期时间。
     * <p>
     * redis.call('expire', KEYS[1], ARGV[2])  expire  秒精度
     * redis.call('pexpire', KEYS[1], ARGV[2]) pexpire 毫秒精度
     */
    // 获取锁的 Lua 脚本
    private static final String LOCK_SCRIPT =
            "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then " +
                    "redis.call('expire', KEYS[1], ARGV[2]); " +
                    "return true; " +
                    "else return false; " +
                    "end";
    // 释放锁的 Lua 脚本
    private static final String UNLOCK_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "redis.call('del', KEYS[1]); " +
                    "return true; " +
                    "else return false; " +
                    "end";

    /**
     * 获取分布式锁
     *
     * @param key
     * @param value
     * @param expireTime 过期时间
     * @return
     */
    public boolean lock(String key, String value, long expireTime) {
        String[] keys = {key};
        String[] args = {value, String.valueOf(expireTime)};
        RedisScript<Boolean> script = new DefaultRedisScript<>(LOCK_SCRIPT, Boolean.class);

        Boolean result = redisTemplate.execute(script, Arrays.asList(keys), args);

//        // 加锁不用lua表达式
//        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.SECONDS);

        System.out.println(key + " - lock result:" + result);
        return result != null && result;
    }

    /**
     * 释放分布式锁
     *
     * @param key
     * @param value
     * @return
     */
    public boolean unlock(String key, String value) {
        String[] keys = {key};
        String[] args = {value};
        RedisScript<Boolean> script = new DefaultRedisScript<>(UNLOCK_SCRIPT, Boolean.class);

        Boolean result = redisTemplate.execute(script, Arrays.asList(keys), args);
        System.out.println(key + " - unlock result:" + result);
        return result != null && result;
    }
}