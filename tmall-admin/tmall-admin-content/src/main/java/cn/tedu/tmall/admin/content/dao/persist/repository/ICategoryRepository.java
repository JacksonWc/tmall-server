package cn.tedu.tmall.admin.content.dao.persist.repository;

import cn.tedu.tmall.admin.content.pojo.entity.Category;
import cn.tedu.tmall.admin.content.pojo.vo.CategoryListItemVO;

import java.util.List;

public interface ICategoryRepository {
    int insertNewCategory(Category category);

    List<CategoryListItemVO> list();
}
