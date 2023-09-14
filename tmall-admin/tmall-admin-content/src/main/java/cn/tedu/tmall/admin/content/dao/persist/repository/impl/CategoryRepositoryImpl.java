package cn.tedu.tmall.admin.content.dao.persist.repository.impl;

import cn.tedu.tmall.admin.content.dao.persist.mapper.CategoryMapper;
import cn.tedu.tmall.admin.content.dao.persist.repository.ICategoryRepository;
import cn.tedu.tmall.admin.content.pojo.entity.Category;
import cn.tedu.tmall.admin.content.pojo.vo.CategoryListItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepositoryImpl implements ICategoryRepository {
    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public int insertNewCategory(Category category) {
        return categoryMapper.insert(category);
    }

    @Override
    public List<CategoryListItemVO> list() {
        return categoryMapper.list();
    }
}
