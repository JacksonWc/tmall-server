package cn.tedu.tmall.admin.content.dao.cache;

import cn.tedu.tmall.admin.content.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.common.cache.CategoryConsts;
import cn.tedu.tmall.common.po.DistrictSimplePO;

import java.util.List;

public interface ICategoryCacheRepository extends CategoryConsts {
    void deleteAll();


    void saveList(List<CategoryListItemVO> list);

    List<CategoryListItemVO> listAll();
}
