package cn.tedu.tmall.admin.mall.dao.persist.repository;

import cn.tedu.tmall.admin.mall.pojo.entity.Category;
import cn.tedu.tmall.common.vo.PageData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategoryRepositoryTests {

    @Autowired
    ICategoryRepository repository;

    @Test
    void insert() {
        Category category = new Category();
        category.setName("大红袍");

        int rows = repository.insert(category);
        System.out.println("插入数据完成，受影响的行数：" + rows);
    }

    @Test
    void listByParent() {
        Long parentId = 1L;
        Integer pageNum = 1;
        Integer pageSize = 3;
        PageData<?> pageData = repository.listByParent(parentId, pageNum, pageSize);
        System.out.println("查询列表完成！");
        System.out.println("数据总量：" + pageData.getTotal());
        System.out.println("当前页码：" + pageData.getCurrentPage());
        System.out.println("每页数据量：" + pageData.getPageSize());
        System.out.println("最大页码：" + pageData.getMaxPage());
        List<?> list = pageData.getList();
        System.out.println("本次查询结果的列表中的数据量：" + list.size());
        for (Object item : list) {
            System.out.println(item);
        }
    }

}
