package cn.tedu.tmall.passport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 设置redis中数据有效期的测试
 */
@SpringBootTest
public class RedisSimpleTests {

    @Autowired
    RedisTemplate<String, Serializable> redisTemplate;

    @Test
    void set() {
        ValueOperations<String, Serializable> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("test", "xxxxx");
    }

    @Test
    void expire() {
        redisTemplate.expire("test", 30, TimeUnit.SECONDS);
    }

    @Test
    void delete() {
        Boolean deleteResult = redisTemplate.delete("test");
        System.out.println(deleteResult);
    }

}
