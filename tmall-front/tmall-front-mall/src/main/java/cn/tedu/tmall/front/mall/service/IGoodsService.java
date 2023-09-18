package cn.tedu.tmall.front.mall.service;

import cn.tedu.tmall.common.consts.data.MallConsts;
import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsSearchVO;
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


    /**
     * 搜索商品
     *
     * @param keyword 关键词
     * @param pageNum 页码
     * @return 搜索的分页结果
     */
    PageData<GoodsSearchVO> search(String keyword, Integer pageNum);

    /**
     * 搜索商品
     *
     * @param keyword  关键词
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return 搜索的分页结果
     */
    PageData<GoodsSearchVO> search(String keyword, Integer pageNum, Integer pageSize);


}
