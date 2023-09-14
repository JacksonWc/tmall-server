package cn.tedu.tmall.front.mall.service;


import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.front.mall.pojo.vo.CartListItemVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 处理购物车业务的接口
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Transactional
public interface ICartService {

    /**
     * 将商品添加到购物车
     *
     * @param currentPrincipal 当事人
     * @param goodsId          商品ID
     * @param goodsNum         商品数量
     */
    void add(CurrentPrincipal currentPrincipal, Long goodsId, Integer goodsNum);

    /**
     * 删除购物车中的商品
     *
     * @param currentPrincipal 当事人
     * @param goodsId          商品ID
     */
    void delete(CurrentPrincipal currentPrincipal, Long goodsId);

    /**
     * 将购物车中商品的数量增加1
     *
     * @param currentPrincipal 当事人
     * @param goodsId          商品ID
     * @return 新的数量
     */
    Integer increaseNum(CurrentPrincipal currentPrincipal, Long goodsId);

    /**
     * 增加购物车中商品的数量
     *
     * @param currentPrincipal 当事人
     * @param goodsId          商品ID
     * @param num              增加的数量
     * @return 新的数量
     */
    Integer increaseNum(CurrentPrincipal currentPrincipal, Long goodsId, Integer num);

    /**
     * 将购物车中商品的数量减少1
     *
     * @param currentPrincipal 当事人
     * @param goodsId          商品ID
     * @return 新的数量
     */
    Integer reduceNum(CurrentPrincipal currentPrincipal, Long goodsId);

    /**
     * 减少购物车中商品的数量
     *
     * @param currentPrincipal 当事人
     * @param goodsId          商品ID
     * @param num              减少的数量
     * @return 新的数量
     */
    Integer reduceNum(CurrentPrincipal currentPrincipal, Long goodsId, Integer num);

    /**
     * 查询购物车列表
     *
     * @param currentPrincipal 当事人
     * @return 购物车列表
     */
    List<CartListItemVO> list(CurrentPrincipal currentPrincipal);

}
