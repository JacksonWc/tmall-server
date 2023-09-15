package cn.tedu.tmall.admin.mall.service;

import cn.tedu.tmall.common.consts.data.MallConsts;
import org.springframework.transaction.annotation.Transactional;

/**
 * 处理订单数据的业务接口
 *
 * @author java@tedu.cn
 * @version 3.0
 */
@Transactional
public interface IOrderService extends MallConsts {

    /**
     * 关闭所有超时未支付订单
     *
     * @return 成功关闭的订单数量
     */
    int closeUnpaid();

}
