package cn.tedu.tmall.basic.dao.cache;

import cn.tedu.tmall.common.consts.cache.DistrictCacheConsts;
import cn.tedu.tmall.common.po.DistrictSimplePO;

import java.util.List;

public interface IDistrictCacheRepository extends DistrictCacheConsts {

    // 向缓存中写入省市区的列表
    void saveListByParent(Long parentId, List<DistrictSimplePO> districtList);

    // 删除缓存中所有省市区的数据
    void deleteAll();

    // 从缓存中读取省市区的列表
    List<DistrictSimplePO> listByParent(Long parentId);

    void save(DistrictSimplePO districtSimplePO);
}
