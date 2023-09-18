package cn.tedu.tmall.admin.mall.startup;

import cn.tedu.tmall.admin.mall.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 处理商品搜索数据的预加载
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@Component
public class GoodsSearchRunner implements ApplicationRunner {

    @Autowired
    private IGoodsService goodsService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("开始执行【重建商品的搜索数据】的数据预热");
        goodsService.rebuildSearch();
    }

}
