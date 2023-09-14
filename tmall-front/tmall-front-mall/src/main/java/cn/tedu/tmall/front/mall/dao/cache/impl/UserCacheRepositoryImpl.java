package cn.tedu.tmall.front.mall.dao.cache.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.tedu.tmall.common.po.UserStatePO;
import cn.tedu.tmall.front.mall.dao.cache.IUserCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class UserCacheRepositoryImpl implements IUserCacheRepository {
    @Value("${tmall.jwt.duration-in-minute}")
    private long durationInMinute;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override
    public void saveUserState(Long id, UserStatePO userStatePO) {
        String key = KEY_PREFIX_USER_STATE + id;
        Map<String, Object> map = BeanUtil.beanToMap(userStatePO);
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        opsForHash.putAll(key, map);
    }

    @Override
    public UserStatePO getUserState(Long id) {
        String key = KEY_PREFIX_USER_STATE+ id;
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        Map<Object, Object> entries = opsForHash.entries(key);
        UserStatePO userStatePO = null;
        if (entries.size() > 0) {
            userStatePO = BeanUtil.mapToBean(entries, UserStatePO.class, true, null);
        }
        return userStatePO;
    }


    @Override
    public boolean deleteUserState(Long id) {
        String key = KEY_PREFIX_USER_STATE + id;
        return redisTemplate.delete(key);
    }

    @Override
    public void expire(Long id) {
        String key = KEY_PREFIX_USER_STATE + id;
        redisTemplate.expire(key, durationInMinute, TimeUnit.MINUTES);
    }
}
