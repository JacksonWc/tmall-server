package cn.tedu.tmall.front.mall.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 标准VO类：订单项信息
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
public class OrderItemStandardVO implements Serializable {

    /**
     * 数据ID
     */
    private Long id;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 商品封面图
     */
    private String goodsCoverUrl;

    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 商品数量
     */
    private Integer goodsNum;

    /**
     * 商品单价
     */
    private BigDecimal saleUnitPrice;

}
