package cn.tedu.tmall.admin.mall.service;

import cn.tedu.tmall.admin.mall.pojo.param.CategoryAddNewParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceTests {

    @Autowired
    ICategoryService service;

    @Test
    void addNew() {
        CategoryAddNewParam category = new CategoryAddNewParam();
        category.setParentId(33L);
        category.setName("数码相机");

        //测试的时候要捕获异常不然测试绿不了，打印的异常信息就是抛出的哪个异常
        try {
            service.addNew(category);
            System.out.println("添加类别成功！");
        } catch (RuntimeException e) {
            System.out.println("添加类别失败！");
            e.printStackTrace();
        }
    }

}
