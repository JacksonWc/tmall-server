package cn.tedu.tmall.admin.mall.service;

import cn.tedu.tmall.admin.mall.pojo.param.CategoryAddNewParam;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.common.vo.PageData;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ICategoryService {

    void addNew(CategoryAddNewParam categoryAddNewParam);

    PageData<CategoryListItemVO> listByParent(Long parentId, Integer pageNum);

    PageData<CategoryListItemVO> listByParent(Long parentId, Integer pageNum, Integer pageSize);

    /**
     * 根据ID删除类别
     *
     * @param id 尝试删除的类别数据的ID
     */
    void delete(Long id);


    /**
     * 启用类别
     *
     * @param id 尝试启用的类别的ID
     */
    void setEnable(Long id);

    /**
     * 禁用类别
     *
     * @param id 尝试禁用的类别的ID
     */
    void setDisable(Long id);

}
