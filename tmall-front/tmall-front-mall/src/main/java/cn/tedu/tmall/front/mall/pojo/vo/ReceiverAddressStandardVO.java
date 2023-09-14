package cn.tedu.tmall.front.mall.pojo.vo;

import lombok.Data;

/**
 * 标准VO类：收货地址
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
public class ReceiverAddressStandardVO {

    /**
     * 数据ID
     */
    private Long id;

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
     * 省名称
     */
    private String province;

    /**
     * 市名称
     */
    private String city;

    /**
     * 区名称
     */
    private String area;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 是否默认
     */
    private Integer isDefault;

}
