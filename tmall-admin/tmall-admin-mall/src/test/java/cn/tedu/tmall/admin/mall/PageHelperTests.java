package cn.tedu.tmall.admin.mall;

import cn.tedu.tmall.admin.mall.dao.persist.mapper.CategoryMapper;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.common.util.PageInfoToPageDataConverter;
import cn.tedu.tmall.common.vo.PageData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PageHelperTests {

    @Autowired
    CategoryMapper mapper;

    @Test
    void listByParent() {
        Long parentId = 1L; // 2~9
        PageHelper.startPage(2, 4);
        List<CategoryListItemVO> list = mapper.listByParent(parentId);
        System.out.println("查询列表完成，列表中的数据量：" + list.size());
        for (Object item : list) {
            System.out.println(item);
        }
        System.out.println(list.getClass().getName()); // com.github.pagehelper.Page（继承arrlist），原来是arrlist
        System.out.println(list);
        System.out.println();

        //除了强转还可用这个构造方法，信息比page的内容还多哦
        PageInfo<CategoryListItemVO> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);
        System.out.println();


        PageData<CategoryListItemVO> pageData = PageInfoToPageDataConverter.convert(pageInfo);
        System.out.println(pageData);
    }

}
