package cn.tedu.tmall.front.mall.dao.persist.repository.impl;


import cn.tedu.tmall.common.util.PageInfoToPageDataConverter;
import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.front.mall.dao.persist.mapper.GoodsMapper;
import cn.tedu.tmall.front.mall.dao.persist.repository.IGoodsRepository;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsStandardVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理商品数据的存储库实现类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@Repository
public class GoodsRepositoryImpl implements IGoodsRepository {

    @Autowired
    private GoodsMapper goodsMapper;

    public GoodsRepositoryImpl() {
        log.info("创建存储库对象：GoodsRepositoryImpl");
    }



    @Override
    public GoodsStandardVO getStandardById(Long id) {
        log.debug("开始执行【根据ID查询商品信息】的数据访问，参数：{}", id);
        return goodsMapper.getStandardById(id);
    }

    @Override
    public PageData<GoodsListItemVO> listByRecommend(Integer pageNum, Integer pageSize) {
        log.debug("开始执行【查询推荐的商品数据列表】的数据访问，页码：{}，每页记录数：{}", pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<GoodsListItemVO> list = goodsMapper.listByRecommend();
        PageInfo<GoodsListItemVO> pageInfo = new PageInfo<>(list);
        return PageInfoToPageDataConverter.convert(pageInfo);
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
