package cn.tedu.tmall.admin.mall.service.impl;

import cn.tedu.tmall.admin.mall.dao.persist.repository.IOrderRepository;
import cn.tedu.tmall.admin.mall.service.IOrderService;
import cn.tedu.tmall.common.consts.data.MallConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 处理订单数据的业务实现类
 *
 * @author java@tedu.cn
 * @version 3.0
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Value("${tmall.order.pay-overtime-in-minute}")
    private int payOvertimeInMinute;
    @Autowired
    private IOrderRepository orderRepository;

    public OrderServiceImpl() {
        log.debug("创建业务类对象：OrderServiceImpl");
    }

    @Override
    public int closeUnpaid() {
        log.debug("开始处理【关闭所有超时未支付订单】的业务，无参数");
        //查出所有超时订单的id
        List<Long> idList = orderRepository.listOvertimeUnpaidIdList(payOvertimeInMinute);
        //更新订单的状态，6代表超时被关闭，返回被关闭的订单数量
        int rows = orderRepository.updateStateByBatchIds(idList, MallConsts.ORDER_STATE_CLOSED_BY_SYSTEM);
        log.debug("成功关闭【{}】条超时未支付的订单", rows);
        return rows;
    }

}
