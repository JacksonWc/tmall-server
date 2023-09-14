package cn.tedu.tmall.front.mall.service;

import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.common.consts.data.MallConsts;
import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.front.mall.pojo.param.OrderAddNewParam;
import cn.tedu.tmall.front.mall.pojo.vo.OrderListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.OrderStandardVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 处理订单数据的业务接口
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Transactional
public interface IOrderService extends MallConsts {

    /**
     * 创建订单
     *
     * @param currentPrincipal 当事人
     * @param orderAddNewParam 创建订单的参数对象
     * @return 订单ID
     */
    Long create(CurrentPrincipal currentPrincipal, OrderAddNewParam orderAddNewParam);

    /**
     * 根据ID查询订单
     *
     * @param currentPrincipal 当事人
     * @param orderId          订单ID
     * @return 订单详情
     */
    OrderStandardVO getStandardById(CurrentPrincipal currentPrincipal, Long orderId);

    /**
     * 查询订单列表，将使用默认的每页记录数
     *
     * @param currentPrincipal 当事人
     * @param pageNum 页码
     * @return 订单列表
     */
    PageData<OrderListItemVO> listByUser(CurrentPrincipal currentPrincipal, Integer pageNum);

    /**
     * 查询订单列表
     *
     * @param currentPrincipal 当事人
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return 订单列表
     */
    PageData<OrderListItemVO> listByUser(CurrentPrincipal currentPrincipal, Integer pageNum, Integer pageSize);

}
