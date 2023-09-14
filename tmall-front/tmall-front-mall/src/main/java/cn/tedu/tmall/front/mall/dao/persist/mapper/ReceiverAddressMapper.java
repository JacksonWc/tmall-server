package cn.tedu.tmall.front.mall.dao.persist.mapper;

import cn.tedu.tmall.front.mall.pojo.entity.ReceiverAddress;
import cn.tedu.tmall.front.mall.pojo.vo.ReceiverAddressListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.ReceiverAddressStandardVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理收货地址数据的Mapper接口
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Repository
public interface ReceiverAddressMapper extends BaseMapper<ReceiverAddress> {

    /**
     * 根据id查询收货地址数据详情
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
