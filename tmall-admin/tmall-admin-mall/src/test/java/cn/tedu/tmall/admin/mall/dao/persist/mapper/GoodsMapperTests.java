package cn.tedu.tmall.admin.mall.dao.persist.mapper;

import cn.tedu.tmall.admin.mall.dao.persist.mapper.GoodsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GoodsMapperTests {

    @Autowired
    GoodsMapper goodsMapper;

    @Test
    void selectTest1() {
        System.out.println(goodsMapper.selectById((long) 1));
    }

    @Test
        //通过商品分类的id查询商品分类名称，联合mall_goods表
    void getCategoryNameByCategoryId() {
        System.out.println(goodsMapper.getCategoryNameByCategoryId((long) 8));
    }

    ;

    @Test
    void listAllGoods() {
        System.out.println(goodsMapper.listAllGoods());
    }
}
