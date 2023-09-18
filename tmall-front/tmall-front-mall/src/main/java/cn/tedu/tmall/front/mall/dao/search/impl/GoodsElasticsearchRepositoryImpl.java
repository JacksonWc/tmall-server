package cn.tedu.tmall.front.mall.dao.search.impl;


import cn.tedu.tmall.common.po.GoodsSearchPO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 处理商品搜索数据的存储库实现接口，本接口不需要实现类
 *
 * 编程参考：https://docs.spring.io/spring-data/elasticsearch/docs/4.2.1/reference/html/#reference
 *
 * @author java@tedu.cn
 * @version 3.0
 */
@Repository
public interface GoodsElasticsearchRepositoryImpl extends ElasticsearchRepository<GoodsSearchPO, Long> {

    @Query("{\n" +
            "    \"bool\": {\n" +
            "      \"should\": [\n" +
            "        {\n" +
            "          \"match\": {\n" +
            "            \"categoryName\": \"?0\"\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"match\": {\n" +
            "            \"barCode\": \"?0\"\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"match\": {\n" +
            "            \"title\": \"?0\"\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"match\": {\n" +
            "            \"brief\": \"?0\"\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"match\": {\n" +
            "            \"keywords\": \"?0\"\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }")
    @Highlight(fields = {@HighlightField(name = "title")},
            parameters = @HighlightParameters(
                    preTags = "<font style='color: red;'>", postTags = "</font>"))
    SearchPage<GoodsSearchPO> customSearch(String keyword, Pageable pageable);

}
