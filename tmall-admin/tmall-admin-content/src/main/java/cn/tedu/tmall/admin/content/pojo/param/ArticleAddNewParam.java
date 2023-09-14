package cn.tedu.tmall.admin.content.pojo.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 新增文章的参数类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
public class ArticleAddNewParam implements Serializable {

    /**
     * 文章类别ID
     */
    private Long categoryId;


    /**
     * 标题
     */
    private String title;


    /**
     * 摘要
     */
    private String brief;


    /**
     * 封面图
     */
    private String coverUrl;

    /**
     * 关键词列表，各关键词使用英文的逗号分隔
     */
    private String keywords;

    /**
     * 排序序号
     */
    private Integer sort;

    /**
     * 详情，和另外一个表content_article_detail有关
     */
    private String detail;

}
