package cn.tedu.tmall.admin.mall.dao.search;

import cn.tedu.tmall.admin.mall.dao.persist.mapper.GoodsMapper;
import cn.tedu.tmall.common.po.GoodsSearchPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;

import java.util.List;

@SpringBootTest
public class GoodsElasticsearchRepositoryTests {

    @Autowired
    GoodsElasticsearchRepository repository;

    @Autowired
    GoodsMapper goodsMapper;

    @Test
    void initData() {
        List<GoodsSearchPO> list = goodsMapper.listSearch();
        repository.saveAll(list);
    }

    @Test
    void save() {
        GoodsSearchPO goodsSearchPO = new GoodsSearchPO();
        goodsSearchPO.setId(997L);
        goodsSearchPO.setCategoryName("绿茶");
        goodsSearchPO.setTitle("2023年明前特级信阳毛尖绿茶 250克（125g*2罐）");

        repository.save(goodsSearchPO);
    }



    @Test
    void test() {
        String str = "礼盒";
        List<GoodsSearchPO> list = repository.test(str);
        System.out.println("查询结果中的数据量：" + list.size());
        for (GoodsSearchPO goodsSearchPO : list) {
            System.out.println(goodsSearchPO);
        }
    }


    // 14,12,10,2,1,15
    @Test
    void testPage() {
        String str = "绿茶";
        Pageable pageable = PageRequest.of(1, 3);
        SearchPage<GoodsSearchPO> searchPage = repository.testPage(str, pageable);
        SearchHits<GoodsSearchPO> searchHits = searchPage.getSearchHits();
        long totalHits = searchHits.getTotalHits();
        System.out.println("list.getContent() >>> " + searchPage.getContent());
        System.out.println("totalHits >>> " + totalHits);
        System.out.println("list.getTotalPages() >>> " + searchPage.getTotalPages());
        System.out.println("list.getNumber() >>> " + searchPage.getNumber());
        System.out.println("list.getSize() >>> " + searchPage.getSize());
    }

    @Test
    void customSearch() {
        String str = "绿茶";
        Pageable pageable = PageRequest.of(0, 20);
        SearchPage<GoodsSearchPO> searchPage = repository.customSearch(str, pageable);
        SearchHits<GoodsSearchPO> searchHits = searchPage.getSearchHits();

        System.out.println("list.getTotalPages() >>> " + searchPage.getTotalPages());
        System.out.println("list.getNumber() >>> " + searchPage.getNumber());
        System.out.println("list.getSize() >>> " + searchPage.getSize());
        System.out.println("searchHits.getTotalHits() >>> " + searchHits.getTotalHits());

        for (SearchHit<GoodsSearchPO> searchHit : searchHits) {
            GoodsSearchPO goodsSearchPO = searchHit.getContent();
            List<String> highlightFields = searchHit.getHighlightField("title");
            if (highlightFields.size() > 0) {
                String s = highlightFields.get(0);
                goodsSearchPO.setTitle(s);
            }
            System.out.println(goodsSearchPO);
        }
    }


}
