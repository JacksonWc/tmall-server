package cn.tedu.tmall.admin.mall.dao.persist.mapper;

import cn.tedu.tmall.admin.mall.pojo.entity.Category;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CategoryMapperTests {

    @Autowired
    CategoryMapper mapper;

    @Test
    void insert() {
        Category category = new Category();
        category.setName("大红袍3");

        int rows = mapper.insert(category);
        System.out.println("插入数据完成，受影响的行数：" + rows);
    }

    @Test
    void deleteById() {
        Long id = 20L;

        int rows = mapper.deleteById(id);
        System.out.println("删除数据完成，受影响的行数：" + rows);
    }

    @Test
    void deleteByMap() {
        // 使用deleteByMap的不足：无法处理逻辑关联，并且，只能是“等于”，汇总一下是可以做一个等于条件或者多个and的等于条件
        Map<String, Object> map = new HashMap<>();
        map.put("id", 6);
        map.put("name", "Test7");

        int rows = mapper.deleteByMap(map);
        System.out.println("删除数据完成，受影响的行数：" + rows);
    }

    @Test
    void delete() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        // wrapper.eq("id", 6); // equals
        // wrapper.ne("id", 8); // not equals
        // wrapper.gt("id", 8); // greater than，不含等于
        wrapper.lt("id", 14); // less than，不含等于

        int rows = mapper.delete(wrapper);
        System.out.println("删除数据完成，受影响的行数：" + rows);
    }

    @Test
    void deleteBatchIds() {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        idList.add(3L);
        idList.add(5L);
        idList.add(7L);
        idList.add(9L);

        int rows = mapper.deleteBatchIds(idList);
        System.out.println("删除数据完成，受影响的行数：" + rows);
    }

    @Test
    void updateById() {
        Category category = new Category();
        category.setId(4L);
        category.setName("大红袍4");

        int rows = mapper.updateById(category);
        System.out.println("修改数据完成，受影响的行数：" + rows);
    }

    @Test
    void selectCount() {
        int count = mapper.selectCount(null); // select count(*) from xxx;
        System.out.println("统计数据完成，数据量：" + count);
    }

    //这样写的好处是可以cv到其他测试类中
    @Test
    void getStandardById() {
        Long id = 16L;

        Object result = mapper.getStandardById(id);
        System.out.println("查询完成，结果：" + result);
    }

    //这样写的好处是可以cv到其他测试类中
    @Test
    void listByParent() {
        Long parentId = 16L;
        List<?> list = mapper.listByParent(parentId);
        System.out.println("查询列表完成，列表中的数据量：" + list.size());
        for (Object item : list) {
            System.out.println(item);
        }
    }

}
