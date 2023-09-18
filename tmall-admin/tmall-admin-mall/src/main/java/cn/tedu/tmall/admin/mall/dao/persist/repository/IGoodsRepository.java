package cn.tedu.tmall.admin.mall.dao.persist.repository;

import cn.tedu.tmall.admin.mall.dao.persist.mapper.GoodsMapper;
import cn.tedu.tmall.admin.mall.pojo.entity.Goods;
import cn.tedu.tmall.admin.mall.pojo.vo.GoodsListItemVO;
import cn.tedu.tmall.admin.mall.pojo.vo.GoodsStandardVO;
import cn.tedu.tmall.common.po.GoodsSearchPO;
import cn.tedu.tmall.common.vo.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


public interface IGoodsRepository {
    int getNewGoods(Goods goods);

    //通过商品分类的id查询商品分类名称，联合mall_goods表
    String getCategoryName(Long categoryId);


    PageData<GoodsListItemVO> listAllGoods(Integer pageNum, Integer defaultQueryPageSize);

    int insert(Goods goods);

    GoodsStandardVO getStandardById(Long id);

    PageData<GoodsListItemVO> listByCategory(Long categoryId, Integer pageNum, Integer defaultQueryPageSize);

    PageData<GoodsSearchPO> listSearch(Integer pageNum, Integer pageSize);
}
