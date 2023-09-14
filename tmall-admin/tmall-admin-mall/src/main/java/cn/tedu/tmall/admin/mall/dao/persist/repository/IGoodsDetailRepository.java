package cn.tedu.tmall.admin.mall.dao.persist.repository;

import cn.tedu.tmall.admin.mall.pojo.entity.GoodsDetail;

/**
 * 处理商品详情数据的存储库接口
 *
 * @author java@tedu.cn
 * @version 2.0
 */
public interface IGoodsDetailRepository {

    /**
     * 插入商品详情数据
     *
     * @param goodsDetail 商品详情数据
     * @return 受影响的行数
     */
    int insert(GoodsDetail goodsDetail);

    /**
     * 根据商品ID删除商品详情数据
     *
     * @param goodsId 商品ID
     * @return 受影响的行数
     */
    int deleteByGoods(Long goodsId);

    /**
     * 根据ID修改商品详情数据
     *
     * @param goodsDetail 封装了商品ID和新商品详情数据的对象
     * @return 受影响的行数
     */
    int updateByGoods(GoodsDetail goodsDetail);

}
