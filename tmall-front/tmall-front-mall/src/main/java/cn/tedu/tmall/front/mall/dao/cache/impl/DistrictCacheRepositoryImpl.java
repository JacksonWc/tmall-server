package cn.tedu.tmall.front.mall.dao.cache.impl;


import cn.tedu.tmall.common.po.DistrictSimplePO;
import cn.tedu.tmall.front.mall.dao.cache.IDistrictCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * 处理省市区数据的缓存访问实现类
 *
 * @author java@tedu.cn
 * @version 2.0
 **/
@Slf4j
@Repository
public class DistrictCacheRepositoryImpl implements IDistrictCacheRepository {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    public DistrictCacheRepositoryImpl() {
        log.info("创建缓存存储库对象：DistrictCacheRepositoryImpl");
    }

    @Override
    public DistrictSimplePO getByCode(String code) {
        log.debug("开始处理【根据省市区的行政代码获取地区数据】的缓存数据访问，参数：{}", code);
        String key = KEY_PREFIX_ITEM + code;
        ValueOperations<String, Serializable> opsForValue = redisTemplate.opsForValue();
        Serializable serializable = opsForValue.get(key);
        return (DistrictSimplePO) serializable;
    }

}
