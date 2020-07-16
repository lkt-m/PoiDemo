package com.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class DemoApplicationTests {
    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        System.out.println(redisTemplate.opsForHash().get("2020-07-15 11:00:00_wp", "58343").toString());
    }

}
