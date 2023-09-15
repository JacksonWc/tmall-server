package cn.tedu.tmall.admin.mall.dao.persist.repository;

import java.util.List;

/**
 * 处理订单数据的存储库接口
 *
 * @author java@tedu.cn
 * @version 3.0
 */
public interface IOrderRepository {

    /**
     * 根据若干个ID批量修改订单状态
     *
     * @param idList   订单ID的集合
     * @param newState 新状态
     * @return 受影响的行数
     * @see cn.tedu.tmall.common.consts.data.MallConsts
     */
    int updateStateByBatchIds(List<Long> idList, Integer newState);

    /**
     * 查询超时未支付的订单的ID的集合
     *
     * @param overtimeInMinute 超时时间，以分钟为单位
     * @return 订单ID的集合
     */
    List<Long> listOvertimeUnpaidIdList(int overtimeInMinute);

}
