package cn.tedu.tmall.admin.mall.dao.search;

import cn.tedu.tmall.common.po.GoodsSearchPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GoodsElasticsearchRepositoryTests {

    @Autowired
    GoodsElasticsearchRepository repository;

    @Test
    void save() {
        GoodsSearchPO goodsSearchPO = new GoodsSearchPO();
        goodsSearchPO.setId(997L);
        goodsSearchPO.setCategoryName("绿茶");
        goodsSearchPO.setTitle("2023年明前特级信阳毛尖绿茶 250克（125g*2罐）");

        repository.save(goodsSearchPO);
    }

}
