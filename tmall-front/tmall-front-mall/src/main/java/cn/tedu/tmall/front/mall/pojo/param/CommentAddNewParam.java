package cn.tedu.tmall.front.mall.pojo.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 新增商品评论的参数类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
public class CommentAddNewParam implements Serializable {

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

}
