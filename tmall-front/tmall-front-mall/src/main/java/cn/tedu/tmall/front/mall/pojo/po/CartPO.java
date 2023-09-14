package cn.tedu.tmall.front.mall.pojo.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 购物车数据类
 *
 * @author java@tedu.cn
 * @version 2.0
 **/
@Data
@NoArgsConstructor
public class CartPO implements Serializable {

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 商品数量
     */
    private Integer goodsNum;

    public CartPO(Object goodsId, Object goodsNum) {
        this.goodsId = Long.valueOf(goodsId.toString());
        this.goodsNum = Integer.valueOf(goodsNum.toString());
    }

}