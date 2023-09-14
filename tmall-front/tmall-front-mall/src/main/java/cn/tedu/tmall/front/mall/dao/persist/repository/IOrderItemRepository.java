package cn.tedu.tmall.front.mall.dao.persist.repository;

import cn.tedu.tmall.front.mall.pojo.entity.OrderItem;

/**
 * 处理订单项数据的存储库接口
 *
 * @author java@tedu.cn
 * @version 2.0
 */
public interface IOrderItemRepository {

    /**
     * 插入订单项数据
     *
     * @param orderItem 订单项数据
     * @return 受影响的行数
     */
    int insert(OrderItem orderItem);

}
