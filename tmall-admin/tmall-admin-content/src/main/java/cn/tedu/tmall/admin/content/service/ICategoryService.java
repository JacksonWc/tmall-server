package cn.tedu.tmall.admin.content.service;

import cn.tedu.tmall.admin.content.pojo.param.CategoryAddNewParam;

public interface ICategoryService {
    int insertNewCategory(CategoryAddNewParam categoryAddNewParam);

    void rebuildCache();
}
