package cn.tedu.tmall.admin.mall.schedule;

import cn.tedu.tmall.admin.mall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 处理订单数据的计划任务
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@Component
public class OrderSchedule {

    @Autowired
    private IOrderService orderService;

    @Scheduled(fixedRate = 50 * 1000) // 自启动项目（含）时，每50秒执行1次
    public void closeUnpaid() {
        log.debug("开始执行【关闭所有超时未支付订单】计划任务");
        orderService.closeUnpaid();
    }

}
