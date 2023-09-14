package cn.tedu.tmall.front.mall.dao.persist.repository;

import cn.tedu.tmall.front.mall.pojo.entity.ReceiverAddress;
import cn.tedu.tmall.front.mall.pojo.vo.ReceiverAddressListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.ReceiverAddressStandardVO;

import java.util.List;

/**
 * 处理收货地址数据的存储库接口
 *
 * @author java@tedu.cn
 * @version 2.0
 */
public interface IReceiverAddressRepository {

    /**
     * 插入收货地址数据
     *
     * @param receiverAddress 收货地址数据
     * @return 受影响的行数
     */
    int insert(ReceiverAddress receiverAddress);

    /**
     * 根据ID删除收货地址数据
     *
     * @param id 收货地址ID
     * @return 受影响的行数
     */
    int deleteById(Long id);

    /**
     * 根据ID修改收货地址数据
     *
     * @param receiverAddress 封装了收货地址ID和新数据的对象
     * @return 受影响的行数
     */
    int update(ReceiverAddress receiverAddress);

    /**
     * 将用户的所有收货地址设置为非默认
     *
     * @param userId 用户ID
     * @return 受影响的行数
     */
    int updateNonDefaultByUser(Long userId);

    /**
     * 根据ID查询收货地址数据详情
     *
     * @param id 收货地址ID
     * @return 匹配的收货地址数据详情，如果没有匹配的数据，则返回null
     */
    ReceiverAddressStandardVO getStandardById(Long id);

    /**
     * 根据用户查询收货地址列表
     *
     * @param userId 收货地址类别的ID
     * @return 收货地址列表
     */
    List<ReceiverAddressListItemVO> listByUser(Long userId);

}
