package cn.tedu.tmall.admin.mall;

import cn.tedu.tmall.admin.mall.dao.persist.mapper.GoodsMapper;
import cn.tedu.tmall.admin.mall.pojo.entity.Goods;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MySqlTests {

    @Autowired
    GoodsMapper mapper;

    @Test
    void insert() {
        for (int i = 1; i < 1000; i++) {
            List<Goods> goodsList = new ArrayList<>();
            for (int i2 = 50000*i+1; i2 < 50000*(i+1); i2++) {
                Goods goods = new Goods();
                goods.setTitle("测试商品标题" + i2);
                goodsList.add(goods);
            }

            long start = System.currentTimeMillis();
//        for (Goods goods : goodsList) {
//            mapper.insert(goods);
//        }
            mapper.insertBatch(goodsList);
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        }

    }

}
