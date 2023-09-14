package cn.tedu.tmall.front.mall.dao.persist.repository;


import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.front.mall.pojo.entity.Order;
import cn.tedu.tmall.front.mall.pojo.vo.OrderListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.OrderStandardVO;

/**
 * 处理订单数据的存储库接口
 *
 * @author java@tedu.cn
 * @version 2.0
 */
public interface IOrderRepository {

    /**
     * 插入订单数据
     *
     * @param order 订单数据
     * @return 受影响的行数
     */
    int insert(Order order);

    /**
     * 根据订单ID查询用户的订单
     *
     * @param orderId 订单ID
     * @param userId  用户ID
     * @return 订单详情
     */
    OrderStandardVO getStandardByIdAndUser(Long orderId, Long userId);

    /**
     * 根据用户查询订单列表
     *
     * @param userId   用户ID
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return 订单列表
     */
    PageData<OrderListItemVO> listByUser(Long userId, Integer pageNum, Integer pageSize);

}
