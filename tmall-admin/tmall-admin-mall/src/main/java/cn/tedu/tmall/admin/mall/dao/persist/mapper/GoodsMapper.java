package cn.tedu.tmall.admin.mall.dao.persist.mapper;

import cn.tedu.tmall.admin.mall.pojo.entity.Goods;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryStandardVO;
import cn.tedu.tmall.admin.mall.pojo.vo.GoodsListItemVO;
import cn.tedu.tmall.admin.mall.pojo.vo.GoodsStandardVO;
import cn.tedu.tmall.common.po.GoodsSearchPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsMapper extends BaseMapper<Goods> {


    //通过商品分类的id查询商品分类名称，联合mall_goods表
    String getCategoryNameByCategoryId(Long categoryId);

    //商品列表的查询
    List<GoodsListItemVO> listAllGoods();

    GoodsStandardVO getStandardById(Long id);

    List<GoodsListItemVO> listByCategory(Long categoryId);

    //批量插入
    int insertBatch(List<Goods> goodsList);

    /**
     * 查询用于搜索的商品数据列表，此搜索结果将用于写入到ES中
     *
     * @return 商品数据列表
     */
    List<GoodsSearchPO> listSearch();
}
