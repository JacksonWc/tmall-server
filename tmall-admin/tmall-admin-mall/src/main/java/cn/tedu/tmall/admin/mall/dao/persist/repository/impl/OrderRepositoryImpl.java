package cn.tedu.tmall.admin.mall.dao.persist.repository.impl;

import cn.tedu.tmall.admin.mall.dao.persist.mapper.OrderMapper;
import cn.tedu.tmall.admin.mall.dao.persist.repository.IOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理订单数据的存储库实现类
 *
 * @author java@tedu.cn
 * @version 3.0
 */
@Slf4j
@Repository
public class OrderRepositoryImpl implements IOrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    public OrderRepositoryImpl() {
        log.info("创建存储库对象：OrderRepositoryImpl");
    }

    @Override
    public int updateStateByBatchIds(List<Long> idList, Integer newState) {
        log.debug("开始执行【根据若干个ID批量修改订单状态】的数据访问，ID列表：{}，新状态：{}", idList, newState);
        return orderMapper.updateStateByBatchIds(idList, newState);
    }

    @Override
    public List<Long> listOvertimeUnpaidIdList(int overtimeInMinute) {
        log.debug("开始执行【查询超时未支付的订单的ID的集合】的数据访问，超时时间：{}分钟", overtimeInMinute);
        return orderMapper.listOvertimeUnpaidIdList(overtimeInMinute);
    }

}
