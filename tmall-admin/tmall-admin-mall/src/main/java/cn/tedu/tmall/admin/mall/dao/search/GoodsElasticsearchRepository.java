package cn.tedu.tmall.admin.mall.dao.search;

import cn.tedu.tmall.common.pojo.po.GoodsSearchPO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsElasticsearchRepository
        extends CrudRepository<GoodsSearchPO, Long> {
}
