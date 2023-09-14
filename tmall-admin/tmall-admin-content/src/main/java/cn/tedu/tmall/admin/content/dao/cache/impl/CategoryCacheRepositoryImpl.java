package cn.tedu.tmall.admin.content.dao.cache.impl;

import cn.tedu.tmall.admin.content.dao.cache.ICategoryCacheRepository;
import cn.tedu.tmall.admin.content.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.common.po.DistrictSimplePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Repository
public class CategoryCacheRepositoryImpl implements ICategoryCacheRepository {
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;


    @Override
    public void deleteAll() {

        SetOperations<String, Serializable> opsForSet = redisTemplate.opsForSet();
        redisTemplate.delete(KEY_PREFIX_CONTENT_CATEGORY);
    }

    @Override
    public void saveList(List<CategoryListItemVO> list) {
        String key =KEY_PREFIX_CONTENT_CATEGORY;
        ListOperations<String, Serializable> opsForList = redisTemplate.opsForList();
        for (CategoryListItemVO categoryListItemVO : list) {
            opsForList.rightPush(key, categoryListItemVO);
        }
    }

    @Override
    public List<CategoryListItemVO> listAll() {
        String key = KEY_PREFIX_CONTENT_CATEGORY;
        long start = 0L;
        long end = -1L;
        ListOperations<String, Serializable> opsForList = redisTemplate.opsForList();
        List range = opsForList.range(key, start, end);
        return range;
    }

}
