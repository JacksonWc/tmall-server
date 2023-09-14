package cn.tedu.tmall.front.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 实体类：订单项（订单中包含的商品）
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
@TableName("mall_order_item")
public class OrderItem implements Serializable {

    /**
     * 数据ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单ID
     */
    private Long orderId;

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

    /**
     * 数据创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 数据最后修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

}
