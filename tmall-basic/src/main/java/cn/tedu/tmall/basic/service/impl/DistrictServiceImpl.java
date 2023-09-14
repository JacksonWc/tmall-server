package cn.tedu.tmall.basic.service.impl;

import cn.tedu.tmall.basic.dao.cache.IDistrictCacheRepository;
import cn.tedu.tmall.basic.dao.persist.repository.IDistrictRepository;
import cn.tedu.tmall.basic.service.IDistrictService;
import cn.tedu.tmall.common.po.DistrictSimplePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    IDistrictRepository districtRepository;
    @Autowired
    private IDistrictCacheRepository districtCacheRepository;

    @Override
    public List<DistrictSimplePO> listByParentId(Long parentId) {
       // return districtRepository.listByParentId(parentId);
        return districtCacheRepository.listByParent(parentId);
    }

    @Override
    public void rebuildCache() {
        districtCacheRepository.deleteAll();

        List<DistrictSimplePO> list = districtRepository.listByParentId(0L);
        districtCacheRepository.saveListByParent(0L, list);

        //这是保存省市区code和中文名称对应关系的需求
        for (DistrictSimplePO districtSimplePO : list) {
            districtCacheRepository.save(districtSimplePO);
        }

        //这是保存根据父级查询子集的需求
        for (DistrictSimplePO districtSimplePO : list) {
            callListRecursion(districtSimplePO);
        }
    }


    private void callListRecursion(DistrictSimplePO district) {
        Long districtId = district.getId();
        List<DistrictSimplePO> list = districtRepository.listByParentId(districtId);
        if (list.size() > 0) {
            districtCacheRepository.saveListByParent(districtId, list);
            for (DistrictSimplePO districtSimplePO : list) {
                districtCacheRepository.save(districtSimplePO);
                callListRecursion(districtSimplePO);
            }
        }
    }
}
