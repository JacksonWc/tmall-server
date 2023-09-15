package cn.tedu.tmall.admin.mall.dao.persist.mapper;


import cn.tedu.tmall.admin.mall.pojo.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理订单数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 3.0
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据若干个ID批量修改订单状态
     *
     * @param idList   订单ID的集合
     * @param newState 新状态
     * @return 受影响的行数
     * @see cn.tedu.tmall.common.consts.data.MallConsts
     */
    int updateStateByBatchIds(@Param("idList") List<Long> idList, @Param("newState") Integer newState);

    /**
     * 查询超时未支付的订单的ID的集合
     *
     * @param overtimeInMinute 超时时间，以分钟为单位
     * @return 订单ID的集合
     */
    List<Long> listOvertimeUnpaidIdList(int overtimeInMinute);

}
