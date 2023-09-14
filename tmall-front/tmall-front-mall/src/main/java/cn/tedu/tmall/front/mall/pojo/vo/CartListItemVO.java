package cn.tedu.tmall.front.mall.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 列表项VO类：商品-购物车
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
@Accessors(chain = true)
public class CartListItemVO implements Serializable {

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 商品简介
     */
    private String goodsBrief;

    /**
     * 商品封面图
     */
    private String goodsCoverUrl;

    /**
     * 商品单价
     */
    private BigDecimal goodsSalePrice;

    /**
     * 商品是否上架
     */
    private Integer goodsIsPutOn;

    /**
     * 商品数量
     */
    private Integer goodsNum;

}