package cn.tedu.tmall.front.mall.dao.cache.impl;

import cn.tedu.tmall.front.mall.dao.cache.ICartCacheRepository;
import cn.tedu.tmall.front.mall.pojo.po.CartPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 处理购物车数据的缓存访问实现类
 *
 * @author java@tedu.cn
 * @version 2.0
 **/
@Slf4j
@Repository
public class CartCacheRepositoryImpl implements ICartCacheRepository {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    public CartCacheRepositoryImpl() {
        log.info("创建缓存存储库对象：CartCacheRepositoryImpl");
    }

    @Override
    public void put(Long userId, CartPO cartPO) {
        log.debug("开始处理【向购物车中存入数据】的缓存数据访问，用户ID：{}，购物车数据：{}", userId, cartPO);
        String key = KEY_PREFIX_CART + userId;
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
//        1对1个对象（对象中又有对象，操作内层对象的散装属性）
        opsForHash.put(key, "" + cartPO.getGoodsId(), cartPO.getGoodsNum());
    }

    @Override
    public long deleteByUserAndGoods(Long userId, Long goodsId) {
        log.debug("开始处理【根据用户与商品删除购物车中的数据】的缓存数据访问，用户ID：{}，商品ID：{}", userId, goodsId);
        String key = KEY_PREFIX_CART + userId;
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        return opsForHash.delete(key, "" + goodsId);
    }

    @Override
    public CartPO getByUserAndGoods(Long userId, Long goodsId) {
        log.debug("开始处理【根据用户与商品查询购物车中的数据】的缓存数据访问，用户ID：{}，商品ID：{}", userId, goodsId);
        CartPO cartPO = null;
        String key = KEY_PREFIX_CART + userId;
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        //这是获取购物车现在对应商品已有的数量
        Object goodsNum = opsForHash.get(key, "" + goodsId);
        if (goodsNum != null) {
            cartPO = new CartPO(goodsId, goodsNum);
        }
        return cartPO;
    }

    @Override
    public List<CartPO> listByUser(Long userId) {
        log.debug("开始处理【根据用户查询购物车数据的列表】的缓存数据访问，用户ID：{}", userId);
        String key = KEY_PREFIX_CART + userId;
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        Map<Object, Object> entries = opsForHash.entries(key);

        List<CartPO> cartList = new ArrayList<>();
        for (Object goodsId : entries.keySet()) {
            Object goodsNum = entries.get(goodsId);
            CartPO cartPO = new CartPO(goodsId, goodsNum);
            cartList.add(cartPO);
        }
        return cartList;
    }

}