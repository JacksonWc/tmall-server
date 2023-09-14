package cn.tedu.tmall.front.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 收货地址的实体类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
@TableName("mall_receiver_address")
public class ReceiverAddress {

    /**
     * 数据ID
     */
    @TableId(type = IdType.AUTO)
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
     * 省编码
     */
    private String provinceCode;

    /**
     * 市名称
     */
    private String city;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区名称
     */
    private String area;

    /**
     * 区编码
     */
    private String areaCode;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 是否默认
     */
    private Integer isDefault;

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
