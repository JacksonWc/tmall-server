package cn.tedu.tmall.admin.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;

@Configuration
public class RedisConfig {
    //RedisConnectionFactory也是自动装配，没有放在属性中，这样是因为自己的类用就行了
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory connectionFactory) {
        //1.创建操作redis的java对象
        RedisTemplate<String, Serializable> redisTemplate =
                new RedisTemplate<>();
        //2.设置连接工厂(访问redis也需要建立连接)
        redisTemplate.setConnectionFactory(connectionFactory);
        //3.设置RedisTemplate对象存取数据时的序列化和反序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.string());
        //序列化器Serializer，要明确放进去什么类型，拿出来什么类型
        redisTemplate.setValueSerializer(RedisSerializer.json());

        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());


        return redisTemplate;
    }
}
