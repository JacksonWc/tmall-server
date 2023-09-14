package cn.tedu.tmall.front.mall.dao.persist.mapper;

import cn.tedu.tmall.front.mall.pojo.entity.Order;
import cn.tedu.tmall.front.mall.pojo.vo.OrderListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.OrderStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理订单数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据订单ID查询用户的订单
     *
     * @param orderId 订单ID
     * @param userId  用户ID
     * @return 订单详情
     */
    OrderStandardVO getStandardByIdAndUser(@Param("orderId") Long orderId, @Param("userId") Long userId);

    /**
     * 根据用户查询订单列表
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    List<OrderListItemVO> listByUser(Long userId);

}
