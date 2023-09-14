package cn.tedu.tmall.front.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 实体类：订单
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
@TableName("mall_order")
public class Order implements Serializable {

    /**
     * 数据ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 买家ID
     */
    private Long buyerId;

    /**
     * 买家用户名
     */
    private String buyerUsername;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 收件人
     */
    private String receiverName;

    /**
     * 收件人电话
     */
    private String receiverPhone;

    /**
     * 收件人地址
     */
    private String receiverAddress;

    /**
     * 商品数量
     */
    private Integer goodsNum;

    /**
     * 商品总价
     */
    private BigDecimal totalPrice;

    /**
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 支付渠道：1=支付宝，2=微信
     */
    private Integer payChannel;

    /**
     * 支付方式：1=在线支付，2=货到付款
     */
    private Integer payMethod;

    /**
     * 订单状态: 0=待支付，1=已支付，待发货, 2=已发货/待收货，3=确认收货/已完成，4=用户关闭，5=平台关闭(商家)，6=系统调度关闭
     */
    private Integer orderState;

    /**
     * 支付时间
     */
    private LocalDateTime gmtPay;

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
