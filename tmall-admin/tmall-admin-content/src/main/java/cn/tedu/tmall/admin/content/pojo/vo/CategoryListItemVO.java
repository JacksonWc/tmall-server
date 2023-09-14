package cn.tedu.tmall.admin.content.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 列表项VO类：内容-类别
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
public class CategoryListItemVO implements Serializable {

    /**
     * 数据ID
     */
    private Long id;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 关键词列表，各关键词使用英文的逗号分隔
     */
    private String keywords;

    /**
     * 排序序号
     */
    private Integer sort;

    /**
     * 是否启用，1=启用，0=未启用
     */
    private Integer enable;

    /**
     * 是否显示在导航栏中，1=启用，0=未启用
     */
    private Integer isDisplay;

}
