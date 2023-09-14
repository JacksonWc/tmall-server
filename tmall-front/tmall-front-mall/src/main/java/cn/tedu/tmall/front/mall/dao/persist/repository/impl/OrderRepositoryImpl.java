package cn.tedu.tmall.front.mall.dao.persist.repository.impl;




import cn.tedu.tmall.common.util.PageInfoToPageDataConverter;
import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.front.mall.dao.persist.mapper.OrderMapper;
import cn.tedu.tmall.front.mall.dao.persist.repository.IOrderRepository;
import cn.tedu.tmall.front.mall.pojo.entity.Order;
import cn.tedu.tmall.front.mall.pojo.vo.OrderListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.OrderStandardVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理订单数据的存储库实现类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@Repository
public class OrderRepositoryImpl implements IOrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    public OrderRepositoryImpl() {
        log.info("创建存储库对象：OrderRepositoryImpl");
    }

    @Override
    public int insert(Order order) {
        log.debug("开始执行【插入订单】的数据访问，参数：{}", order);
        return orderMapper.insert(order);
    }

    @Override
    public OrderStandardVO getStandardByIdAndUser(Long orderId, Long userId) {
        log.debug("开始执行【根据订单ID查询用户的订单】的数据访问，订单：{}，用户：{}", orderId, userId);
        return orderMapper.getStandardByIdAndUser(orderId, userId);
    }

    @Override
    public PageData<OrderListItemVO> listByUser(Long userId, Integer pageNum, Integer pageSize) {
        log.debug("开始执行【根据用户查询订单列表】的数据访问，用户：{}，页码：{}，每页记录数：{}", userId, pageNum, pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<OrderListItemVO> list = orderMapper.listByUser(userId);
        PageInfo<OrderListItemVO> pageInfo = new PageInfo<>(list);
        return PageInfoToPageDataConverter.convert(pageInfo);
    }

}
