package cn.tedu.tmall.admin.content.service;

import cn.tedu.tmall.admin.content.pojo.param.CategoryAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceTests {
    @Autowired
    ICategoryService categoryService;

    @Test
    void insertNewCategory(){
        CategoryAddNewParam categoryAddNewParam=new CategoryAddNewParam();
        System.out.println(categoryService.insertNewCategory(categoryAddNewParam));

    }
}
