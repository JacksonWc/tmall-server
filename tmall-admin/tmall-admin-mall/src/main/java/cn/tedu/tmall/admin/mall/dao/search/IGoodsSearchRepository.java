package cn.tedu.tmall.admin.mall.dao.search;


import cn.tedu.tmall.common.po.GoodsSearchPO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 处理商品搜索数据的存储库接口
 *
 * @author java@tedu.cn
 * @version 3.0
 */
@Repository
public interface IGoodsSearchRepository
        extends ElasticsearchRepository<GoodsSearchPO, Long> {

}