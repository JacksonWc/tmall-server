package cn.tedu.tmall.front.mall.service;

import cn.tedu.tmall.common.consts.data.MallConsts;
import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsStandardVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 处理商品数据的业务接口
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Transactional
public interface IGoodsService extends MallConsts {

    /**
     * 根据ID查询商品
     *
     * @param id 商品ID
     * @return 匹配的商品数据详情，如果没有匹配的数据，则返回null
     */
    GoodsStandardVO getStandardById(Long id);


    PageData<GoodsListItemVO> listByRecommend(Integer pageNum);

    PageData<GoodsListItemVO> listByCategory(Long categoryId, Integer pageNum);
}
