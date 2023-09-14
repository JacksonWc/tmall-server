package cn.tedu.tmall.basic.service;

import cn.tedu.tmall.common.po.DistrictSimplePO;

import java.util.List;

public interface IDistrictService {

    List<DistrictSimplePO> listByParentId(Long parentId);

    /**
     * 重建省市区数据的缓存
     */
    void rebuildCache();
}
