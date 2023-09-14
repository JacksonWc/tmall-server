package cn.tedu.tmall.front.mall.dao.persist.repository.impl;

import cn.tedu.tmall.front.mall.dao.persist.mapper.OrderItemMapper;
import cn.tedu.tmall.front.mall.dao.persist.repository.IOrderItemRepository;
import cn.tedu.tmall.front.mall.pojo.entity.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 处理订单数据的存储库实现类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@Repository
public class OrderItemRepositoryImpl implements IOrderItemRepository {

    @Autowired
    private OrderItemMapper orderItemMapper;

    public OrderItemRepositoryImpl() {
        log.info("创建存储库对象：OrderItemRepositoryImpl");
    }

    @Override
    public int insert(OrderItem orderItem) {
        log.debug("开始执行【插入订单项】的数据访问，参数：{}", orderItem);
        return orderItemMapper.insert(orderItem);
    }

}
