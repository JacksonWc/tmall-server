package cn.tedu.tmall.admin.content.dao.persist.mapper;

import cn.tedu.tmall.admin.content.pojo.entity.Category;
import cn.tedu.tmall.admin.content.pojo.vo.CategoryListItemVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {


     //select id, name, keywords, sort, enable, is_display  from content_category
    List<CategoryListItemVO> list();
}
