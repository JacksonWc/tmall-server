package cn.tedu.tmall.admin.mall.dao.persist.repository;

import cn.tedu.tmall.admin.mall.pojo.entity.Category;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryStandardVO;
import cn.tedu.tmall.common.vo.PageData;

public interface ICategoryRepository {

    /* -------------下面是商品的category新增相关的方法-----------*/
//    插入category一行数据的方法
    int insert(Category category);




    /* -------------下面是商品的category删除相关的方法-----------*/

    /**
     * 根据id删除类别数据
     *
     * @param id 类别ID
     * @return 受影响的行数
     */
    int deleteById(Long id);


    /* -------------下面是商品的category修改相关的方法-----------*/
//    根据id更新category的方法
    int updateById(Category category);


    /* -------------下面是商品的category统计查询的方法-----------*/
//    根据category的名字统计数量的方法
    int countByName(String name);

    /**
     * 根据父级类别，统计其子级类别的数量
     *
     * @param parentId 父级类别的ID
     * @return 此类别的子级类别的数量
     */
    int countByParent(Long parentId);


    /* -------------下面是商品的category单个VO查询的方法-----------*/
//    通过id得到CategoryStandardVO的方法
    CategoryStandardVO getStandardById(Long id);


    /* -------------下面是商品的category的VO列表查询的方法-----------*/
    //分页查询
    PageData<CategoryListItemVO> listByParent(
            Long parentId, Integer pageNum, Integer pageSize
    );

}
