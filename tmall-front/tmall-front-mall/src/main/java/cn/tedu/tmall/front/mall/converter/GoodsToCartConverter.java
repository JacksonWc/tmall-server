package cn.tedu.tmall.front.mall.converter;

import cn.tedu.tmall.front.mall.pojo.po.CartPO;
import cn.tedu.tmall.front.mall.pojo.vo.CartListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsStandardVO;

/**
 * 将商品数据转换为购物车显示对象的转换器
 *
 * @author java@tedu.cn
 * @version 2.0
 **/
public class GoodsToCartConverter {

    /**
     * 将商品数据转换为购物车显示对象
     * @param cartPO 购物车数据
     * @param goods 商品数据
     * @return 购物车显示对象
     */
    public synchronized static CartListItemVO convertToCart(CartPO cartPO, GoodsStandardVO goods) {
        return new CartListItemVO()
                .setGoodsId(cartPO.getGoodsId())
                .setGoodsTitle(goods.getTitle())
                .setGoodsBrief(goods.getBrief())
                .setGoodsCoverUrl(goods.getCoverUrl())
                .setGoodsSalePrice(goods.getSalePrice())
                .setGoodsIsPutOn(goods.getIsPutOn())
                .setGoodsNum(cartPO.getGoodsNum());
    }

}