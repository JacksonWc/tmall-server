package cn.tedu.tmall.basic.dao.persist.mapper;


import cn.tedu.tmall.common.po.DistrictSimplePO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictMapper {
    //根据父级id查询自己的区域vo信息
    //select id, concat(name, suffix) as name,code,  pinyin from dict_district
    //where  parent_id=0 order by sort
    List<DistrictSimplePO> listByParentId(Long parentId);
}
