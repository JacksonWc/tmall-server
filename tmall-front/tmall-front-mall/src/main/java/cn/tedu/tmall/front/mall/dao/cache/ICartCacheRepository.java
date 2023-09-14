package cn.tedu.tmall.front.mall.dao.cache;

import cn.tedu.tmall.common.consts.cache.CartCacheConsts;
import cn.tedu.tmall.front.mall.pojo.po.CartPO;

import java.util.List;

/**
 * 处理购物车缓存数据的存储库接口
 *
 * @author java@tedu.cn
 * @version 2.0
 */
public interface ICartCacheRepository extends CartCacheConsts {

    /**
     * 向购物车中存入数据，即用于增加，也用于修改
     *
     * @param userId 用户ID
     * @param cartPO 购物车数据
     */
    void put(Long userId, CartPO cartPO);

    /**
     * 根据用户与商品删除购物车中的数据
     *
     * @param userId  用户ID
     * @param goodsId 商品ID
     * @return 成功删除的数据量
     */
    long deleteByUserAndGoods(Long userId, Long goodsId);

    /**
     * 根据用户与商品查询购物车中的数据
     *
     * @param userId  用户ID
     * @param goodsId 商品ID
     * @return 购物车数据
     */
    CartPO getByUserAndGoods(Long userId, Long goodsId);

    /**
     * 根据用户查询购物车数据的列表
     *
     * @param userId 用户ID
     * @return 匹配的用户的购物车数据的列表
     */
    List<CartPO> listByUser(Long userId);

}
