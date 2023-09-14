package cn.tedu.tmall.passport;


import cn.hutool.core.bean.BeanUtil;
import cn.tedu.tmall.common.po.UserStatePO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//@Import(RedisConfiguration.class)
@SpringBootTest
public class RedisUserPOTests {

    @Autowired
    RedisTemplate<String, Serializable> redisTemplate;


    @Test
    void testGetRedisConnection(){
        RedisConnection connection =
                redisTemplate.getConnectionFactory().getConnection();
        String ping = connection.ping();
        System.out.println("ping="+ping);
    }

    //手写对象和map的映射关系，完成redis的存和取
    @Test
    void hashPut() {
        UserStatePO userStatePO = new UserStatePO();
        userStatePO.setEnable(1);
        userStatePO.setAuthoritiesJsonString("test");

        Map<String, Object> map = new HashMap<>();
        map.put("enable", userStatePO.getEnable());
        map.put("authoritiesJsonString", userStatePO.getAuthoritiesJsonString());

        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        String key = "passport:user-state:9528";
        opsForHash.putAll(key, map);
    }

    @Test
    void entries() {
        String key = "passport:user-state:9528";
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        Map<Object, Object> entries = opsForHash.entries(key);
        System.out.println(entries);
        System.out.println();

        UserStatePO userStatePO = new UserStatePO();
        userStatePO.setEnable(Integer.valueOf(entries.get("enable").toString()));
        userStatePO.setAuthoritiesJsonString(entries.get("authoritiesJsonString").toString());
        System.out.println(userStatePO);
    }

    // ===== 以下使用了hutool实现Bean与Map的转换 =====

    @Test
    void hashPut2() {
        UserStatePO userStatePO = new UserStatePO();
        userStatePO.setEnable(1);
        userStatePO.setAuthoritiesJsonString("test2");

        Map<String, Object> map = BeanUtil.beanToMap(userStatePO);

        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        String key = "passport:user-state:9527";
        opsForHash.putAll(key, map);
    }

    @Test
    void entries2() {
        String key = "passport:user-state:9527";
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        Map<Object, Object> entries = opsForHash.entries(key);
        System.out.println(entries);
        System.out.println();

        UserStatePO userStatePO = BeanUtil.mapToBean(entries, UserStatePO.class, true, null);
        System.out.println(userStatePO);
    }

}
