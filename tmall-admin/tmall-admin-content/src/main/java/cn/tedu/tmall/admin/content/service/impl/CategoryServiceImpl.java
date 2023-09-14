package cn.tedu.tmall.admin.content.service.impl;

import cn.tedu.tmall.admin.content.dao.cache.ICategoryCacheRepository;
import cn.tedu.tmall.admin.content.dao.persist.repository.ICategoryRepository;
import cn.tedu.tmall.admin.content.pojo.entity.Category;
import cn.tedu.tmall.admin.content.pojo.param.CategoryAddNewParam;
import cn.tedu.tmall.admin.content.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.admin.content.service.ICategoryService;
import cn.tedu.tmall.common.po.DistrictSimplePO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    ICategoryRepository categoryRepository;
    @Autowired
    ICategoryCacheRepository categoryCacheRepository;



    @Override
    public int insertNewCategory(CategoryAddNewParam categoryAddNewParam) {
        Category category=new Category();
        //TODO 前端传过来的，需要验证的字段


        BeanUtils.copyProperties(categoryAddNewParam,category);

        //服务器端设置的字段,无
        return categoryRepository.insertNewCategory(category);
    }

    @Override
    public void rebuildCache() {
        categoryCacheRepository.deleteAll();

        List<CategoryListItemVO> list =categoryRepository.list();
        categoryCacheRepository.saveList( list);


    }


}
