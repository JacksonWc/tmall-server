package cn.tedu.tmall.front.mall.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 列表项VO类：内容-评论
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
public class CommentListItemVO implements Serializable {

    /**
     * 数据ID
     */
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
     * 商品ID
     */
    private Long goodsId;

    /**
     * 评论类型：0=好评，1=中评，2=差评
     */
    private Integer type;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 数据创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm.ss")
    private LocalDateTime gmtCreate;

}
