package cn.tedu.tmall.admin.mall.service;

import cn.tedu.tmall.admin.mall.pojo.param.GoodsAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GoodsServiceTests {
    @Autowired
    IGoodsService goodsService;

    @Test
        //新增商品的测试
    void getNewGoods() {
        GoodsAddNewParam goodsAddNewParam = new GoodsAddNewParam();
        goodsAddNewParam.setCategoryId((long) 8);
        //System.out.println(goodsService.getNewGoods(goodsAddNewParam));
    }
}
