package cn.tedu.tmall.front.mall.alipay.pojo.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 支付参数，由于各参数值将作为JSON数据的值在网络上传输，所以不必关心数据类型，使用Object类型即可
 */
@Data
public class PayParam implements Serializable {

    /**
     * 【支付宝要求】商户订单号，商家自定义，保持唯一性
     */
    private Object outTradeNo;
    /**
     * 【支付宝要求】订单标题，不可使用特殊符号
     */
    private Object subject;
    /**
     * 【支付宝要求】支付金额，最小值0.01元
     */
    private Object totalAmount;

}
