package cn.tedu.tmall.front.mall.dao.persist.repository;


import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsStandardVO;

/**
 * 处理商品数据的存储库接口
 *
 * @author java@tedu.cn
 * @version 2.0
 */
public interface IGoodsRepository {



    /**
     * 根据id查询商品数据详情
     *
     * @param id 商品ID
     * @return 匹配的商品数据详情，如果没有匹配的数据，则返回null
     */
    GoodsStandardVO getStandardById(Long id);


    PageData<GoodsListItemVO> listByRecommend(Integer pageNum, Integer defaultQueryPageSize);

    PageData<GoodsListItemVO> listByCategory(Long categoryId, Integer pageNum, Integer defaultQueryPageSize);
}
