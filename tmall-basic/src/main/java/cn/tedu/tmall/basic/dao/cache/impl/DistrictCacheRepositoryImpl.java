package cn.tedu.tmall.basic.dao.cache.impl;

import cn.tedu.tmall.basic.dao.cache.IDistrictCacheRepository;
import cn.tedu.tmall.common.po.DistrictSimplePO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository
public class DistrictCacheRepositoryImpl implements IDistrictCacheRepository {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override
    public void saveListByParent(Long parentId, List<DistrictSimplePO> districtList) {
        String key = KEY_PREFIX_LIST_BY_PARENT + parentId;

        //ListOperations 类，提供 Redis List API 操作
        ListOperations<String, Serializable> opsForList = redisTemplate.opsForList();
        for (DistrictSimplePO districtSimplePO : districtList) {
            opsForList.rightPush(key, districtSimplePO);
        }
        //SetOperations 类，提供 Redis Set API 操作--SetOperations<String, Serializable> opsForSet = redisTemplate.opsForSet();
        SetOperations<String, Serializable> opsForSet = redisTemplate.opsForSet();
        opsForSet.add(KEY_ALL_KEYS, key);
    }

    @Override
    public void deleteAll() {
        SetOperations<String, Serializable> opsForSet = redisTemplate.opsForSet();
        Set keys = opsForSet.members(KEY_ALL_KEYS);
        redisTemplate.delete(keys);
    }

    @Override
    public List<DistrictSimplePO> listByParent(Long parentId) {
        String key = KEY_PREFIX_LIST_BY_PARENT + parentId;
        long start = 0L;
        long end = -1L;
        ListOperations<String, Serializable> opsForList = redisTemplate.opsForList();
        List range = opsForList.range(key, start, end);
        return range;
    }

    @Override
    public void save(DistrictSimplePO districtSimplePO) {
        log.debug("开始处理【存储地区数据】的缓存数据访问，地区数据：{}", districtSimplePO);
        String key = KEY_PREFIX_ITEM + districtSimplePO.getCode();


        SetOperations<String, Serializable> opsForSet = redisTemplate.opsForSet();
        opsForSet.add(KEY_ALL_KEYS, key);
//        ValueOperations 类，提供 Redis String API 操作--ValueOperations<String, Serializable> opsForValue = redisTemplate.opsForValue();
        ValueOperations<String, Serializable> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key, districtSimplePO);
    }

}
