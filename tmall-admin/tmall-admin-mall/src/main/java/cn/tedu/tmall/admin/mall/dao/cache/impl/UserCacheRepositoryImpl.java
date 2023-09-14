package cn.tedu.tmall.admin.mall.dao.cache.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.tedu.tmall.admin.mall.dao.cache.IUserCacheRepository;
import cn.tedu.tmall.common.po.UserStatePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Map;

@Repository
public class UserCacheRepositoryImpl implements IUserCacheRepository {

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
        String key = KEY_PREFIX_USER_STATE + id;
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        Map<Object, Object> entries = opsForHash.entries(key);
        UserStatePO userStatePO = null;
        if (entries.size() > 0) {
            userStatePO = BeanUtil.mapToBean(entries, UserStatePO.class, true, null);
        }
        return userStatePO;
    }

}
