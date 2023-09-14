package cn.tedu.tmall.admin.content.dao.persist.category;

import cn.tedu.tmall.admin.content.dao.persist.mapper.CategoryMapper;
import cn.tedu.tmall.admin.content.service.ICategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
public class CategoryTests {
    @Autowired
    DataSource dataSource;
    @Autowired
    CategoryMapper categoryMapper;

    @Test
    void getConnection(){
        try {
            dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void list(){
        System.out.println(categoryMapper.list());
    }
}
