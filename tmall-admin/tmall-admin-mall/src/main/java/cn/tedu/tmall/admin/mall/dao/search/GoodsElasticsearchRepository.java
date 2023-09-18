package cn.tedu.tmall.admin.mall.dao.search;


import cn.tedu.tmall.common.po.GoodsSearchPO;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsElasticsearchRepository
        extends ElasticsearchRepository<GoodsSearchPO, Long> {

    List<GoodsSearchPO> findByTitle(String x);



    @Query("{\n" +
            "    \"bool\": {\n" +
            "      \"should\": [\n" +
            "        {\n" +
            "          \"match\": {\n" +
            "            \"title\": \"?0\"\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"match\": {\n" +
            "            \"brief\": \"?0\"\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }")
        // findByTitleOrBrief(String title, String brief);
    List<GoodsSearchPO> test(String x);

}
