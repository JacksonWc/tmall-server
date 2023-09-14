package cn.tedu.tmall.admin.mall.dao.persist.repository.impl;

import cn.tedu.tmall.admin.mall.dao.persist.mapper.CategoryMapper;
import cn.tedu.tmall.admin.mall.dao.persist.repository.ICategoryRepository;
import cn.tedu.tmall.admin.mall.pojo.entity.Category;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryStandardVO;
import cn.tedu.tmall.common.util.PageInfoToPageDataConverter;
import cn.tedu.tmall.common.vo.PageData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CategoryRepositoryImpl implements ICategoryRepository {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public int insert(Category category) {
        return categoryMapper.insert(category);
    }

    @Override
    public int updateById(Category category) {
        return categoryMapper.updateById(category);
    }

    @Override
    public int countByName(String name) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return categoryMapper.selectCount(queryWrapper);
    }

    @Override
    public CategoryStandardVO getStandardById(Long id) {
        return categoryMapper.getStandardById(id);
    }

    @Override
    public PageData<CategoryListItemVO> listByParent(Long parentId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CategoryListItemVO> list = categoryMapper.listByParent(parentId);
        PageInfo<CategoryListItemVO> pageInfo = new PageInfo(list);
        return PageInfoToPageDataConverter.convert(pageInfo);
    }

    @Override
    public int deleteById(Long id) {
        log.debug("开始执行【根据ID删除类别】的数据访问，参数：{}", id);
        return categoryMapper.deleteById(id);
    }

    @Override
    public int countByParent(Long parentId) {
        log.debug("开始执行【统计匹配父级的类别的数量】的数据访问，父级类别：{}", parentId);
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        return categoryMapper.selectCount(queryWrapper);
    }

}
