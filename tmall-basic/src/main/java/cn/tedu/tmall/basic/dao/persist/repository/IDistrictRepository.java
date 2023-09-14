package cn.tedu.tmall.basic.dao.persist.repository;

import cn.tedu.tmall.common.po.DistrictSimplePO;

import java.util.List;

public interface IDistrictRepository {

    List<DistrictSimplePO> listByParentId(Long parentId);
}
