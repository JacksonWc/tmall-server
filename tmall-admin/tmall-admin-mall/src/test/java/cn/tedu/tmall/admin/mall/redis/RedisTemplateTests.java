package cn.tedu.tmall.admin.mall.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Map;

@SpringBootTest
public class RedisTemplateTests {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    void testGetRedisConnection() {
        RedisConnection connection =
                redisTemplate.getConnectionFactory().getConnection();
        String ping = connection.ping();
        System.out.println("ping=" + ping);
    }

    /**
     * 使用ValueOperations进行String类型数据操作的演示
     */
    @Test
    void testStringOper() {
        ValueOperations vo = redisTemplate.opsForValue();
        //这种形式的值不能直接使用increment,因为本来key和value都是序列化之后的
        vo.set("id", 100);
        Object id = vo.get("id");
        System.out.println("id=" + id);

        Long count = vo.increment("count", 1L);
        count = vo.increment("count", 1L);
        System.out.println("count=" + count);
        //.....
    }


    /**
     * 直接存储对象
     */
    @Test
    void testStringOper01() {
        //redisTemplate.setKeySerializer(RedisSerializer.string());
        //redisTemplate.setValueSerializer(RedisSerializer.json());
        ValueOperations vo = redisTemplate.opsForValue();
        //.....
        Point p1 = new Point();
        p1.setX(10);
        p1.setY(20);
        vo.set("p1", p1);
        Point p2 = (Point) vo.get("p1");
        System.out.println(p1 == p2);
        System.out.println(p1.equals(p2));
    }

    /**
     * hash操作
     */
    @Test
    void testHashOper02() {
        //redisTemplate.setKeySerializer(RedisSerializer.string());
        //redisTemplate.setValueSerializer(RedisSerializer.json());
        //redisTemplate.setHashKeySerializer(RedisSerializer.string());
        //redisTemplate.setHashValueSerializer(RedisSerializer.json());
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("cart:101", "2001", 3);
        hashOperations.put("cart:101", "2002", 5);
        Object o1 = hashOperations.get("cart:101", "2001");
        System.out.println(o1);
        Map entries = hashOperations.entries("cart:101");
        System.out.println(entries);
    }
}
