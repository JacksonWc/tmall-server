package cn.tedu.tmall.admin.mall.dao.persist.repository.impl;

import cn.tedu.tmall.admin.mall.dao.persist.mapper.GoodsMapper;
import cn.tedu.tmall.admin.mall.dao.persist.repository.IGoodsRepository;
import cn.tedu.tmall.admin.mall.pojo.entity.Goods;
import cn.tedu.tmall.admin.mall.pojo.vo.GoodsListItemVO;
import cn.tedu.tmall.admin.mall.pojo.vo.GoodsStandardVO;
import cn.tedu.tmall.common.util.PageInfoToPageDataConverter;
import cn.tedu.tmall.common.vo.PageData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class GoodsRepositoryImpl implements IGoodsRepository {
    @Autowired
    GoodsMapper goodsMapper;

    /* ------------------------新增商品的方法----------------*/
    @Override
    public int getNewGoods(Goods goods) {
        return goodsMapper.insert(goods);
    }


    /* ------------------------查询商品相关的方法----------------*/
    //通过商品分类的id查询商品分类名称，联合mall_goods表
    @Override
    public String getCategoryName(Long categoryId) {
        return goodsMapper.getCategoryNameByCategoryId(categoryId);
    }

    @Override
    public PageData<GoodsListItemVO> listAllGoods(Integer pageNum, Integer defaultQueryPageSize) {
        System.out.println("defaultQueryPageSize的值是" + defaultQueryPageSize + ",pageNum的值是" + pageNum);
        PageHelper.startPage(pageNum, defaultQueryPageSize);
        List<GoodsListItemVO> goodsListItemVOS = goodsMapper.listAllGoods();
        PageInfo<GoodsListItemVO> pageInfo = new PageInfo(goodsListItemVOS);
        return PageInfoToPageDataConverter.convert(pageInfo);
    }

    @Override
    public int insert(Goods goods) {
        return goodsMapper.insert(goods);
    }

    @Override
    public GoodsStandardVO getStandardById(Long id) {
        log.debug("开始执行【根据ID查询商品信息】的数据访问，参数：{}", id);
        return goodsMapper.getStandardById(id);
    }

    @Override
    public PageData<GoodsListItemVO> listByCategory(Long categoryId, Integer pageNum, Integer pageSize) {
        log.debug("开始执行【根据类别查询商品列表】的数据访问，商品类别：{}，页码：{}，每页记录数：{}", categoryId, pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<GoodsListItemVO> list = goodsMapper.listByCategory(categoryId);
        PageInfo<GoodsListItemVO> pageInfo = new PageInfo<>(list);
        return PageInfoToPageDataConverter.convert(pageInfo);
    }


}
