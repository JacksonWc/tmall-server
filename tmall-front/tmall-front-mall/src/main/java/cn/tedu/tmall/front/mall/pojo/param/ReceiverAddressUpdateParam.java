package cn.tedu.tmall.front.mall.pojo.param;

import lombok.Data;

/**
 * 添加收货地址的参数类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
public class ReceiverAddressUpdateParam {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 收货人
     */
    private String receiverName;

    /**
     * 收货电话
     */
    private String receiverPhone;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区编码
     */
    private String areaCode;

    /**
     * 详细地址
     */
    private String detail;

}
