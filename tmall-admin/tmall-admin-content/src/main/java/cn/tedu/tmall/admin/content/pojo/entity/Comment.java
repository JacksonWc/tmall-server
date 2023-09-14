package cn.tedu.tmall.admin.content.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类：内容-评论
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
@TableName("content_comment")
public class Comment implements Serializable {

    /**
     * 数据ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 作者名字
     */
    private String authorName;

    /**
     * 资源类型，0=文章，1=评论
     */
    private Integer resourceType;

    /**
     * 资源ID
     */
    private Long resourceId;

    /**
     * 资源摘要，截取的文章标题或评论
     */
    private String resourceBrief;

    /**
     * 评论内容
     */
    private String content;

    /**
     * IP
     */
    private String ip;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 顶数量
     */
    private Integer upCount;

    /**
     * 踩数量
     */
    private Integer downCount;

    /**
     * 审核状态，0=未审核，1=审核通过，2=拒绝审核
     */
    private Integer checkState;

    /**
     * 显示状态，0=不显示，1=显示
     */
    private Integer isDisplay;

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
