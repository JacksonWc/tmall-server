package cn.tedu.tmall.basic.dao.persist.repository.impl;

import cn.tedu.tmall.basic.dao.persist.mapper.DistrictMapper;
import cn.tedu.tmall.basic.dao.persist.repository.IDistrictRepository;
import cn.tedu.tmall.common.po.DistrictSimplePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DistrictRepositoryImpl implements IDistrictRepository {
    @Autowired
    DistrictMapper districtMapper;

    @Override
    public List<DistrictSimplePO> listByParentId(Long parentId) {
        return districtMapper.listByParentId(parentId);
    }
}
