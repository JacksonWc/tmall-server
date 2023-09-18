package cn.tedu.tmall.admin.mall.schedule;

import cn.tedu.tmall.admin.mall.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 处理商品数据的计划任务
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@Component
public class GoodsSchedule {

    @Autowired
    private IGoodsService goodsService;

    @Scheduled(cron = "0 30 2 * * ?") // 每日凌晨2:30执行
    public void rebuildSearch() {
        log.debug("开始执行【重建商品的搜索数据】计划任务");
        goodsService.rebuildSearch();
    }

}
