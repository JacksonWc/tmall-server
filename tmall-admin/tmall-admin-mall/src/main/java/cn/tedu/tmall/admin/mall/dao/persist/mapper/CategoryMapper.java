package cn.tedu.tmall.admin.mall.dao.persist.mapper;

import cn.tedu.tmall.admin.mall.pojo.entity.Category;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    CategoryStandardVO getStandardById(Long id);

    List<CategoryListItemVO> listByParent(Long parentId);


}
