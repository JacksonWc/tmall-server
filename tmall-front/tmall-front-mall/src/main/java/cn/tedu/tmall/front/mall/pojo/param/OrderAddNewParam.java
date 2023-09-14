package cn.tedu.tmall.front.mall.pojo.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建订单的参数类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
public class OrderAddNewParam implements Serializable {

    /**
     * 收货地址ID
     */
    private Long receiverAddressId;

    /**
     * 商品ID的list
     */
    private Long[] goodsIdList;

}
