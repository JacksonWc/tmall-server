package cn.tedu.tmall.front.mall.dao.persist.repository.impl;

import cn.tedu.tmall.front.mall.dao.persist.mapper.ReceiverAddressMapper;
import cn.tedu.tmall.front.mall.dao.persist.repository.IReceiverAddressRepository;
import cn.tedu.tmall.front.mall.pojo.entity.ReceiverAddress;
import cn.tedu.tmall.front.mall.pojo.vo.ReceiverAddressListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.ReceiverAddressStandardVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理收货地址数据的存储库实现类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@Repository
public class ReceiverAddressRepositoryImpl implements IReceiverAddressRepository {

    @Autowired
    private ReceiverAddressMapper receiverAddressMapper;

    public ReceiverAddressRepositoryImpl() {
        log.info("创建存储库对象：ReceiverAddressRepositoryImpl");
    }

    @Override
    public int insert(ReceiverAddress receiverAddress) {
        log.debug("开始执行【插入收货地址】的数据访问，参数：{}", receiverAddress);
        return receiverAddressMapper.insert(receiverAddress);
    }

    @Override
    public int deleteById(Long id) {
        log.debug("开始执行【根据ID修改收货地址数据】的数据访问，参数：{}", id);
        return receiverAddressMapper.deleteById(id);
    }

    @Override
    public int update(ReceiverAddress receiverAddress) {
        log.debug("开始执行【根据ID修改收货地址数据】的数据访问，参数：{}", receiverAddress);
        return receiverAddressMapper.updateById(receiverAddress);
    }

    @Override
    public int updateNonDefaultByUser(Long userId) {
        log.debug("开始执行【将用户的所有收货地址设置为非默认】的数据访问，参数：{}", userId);
        ReceiverAddress receiverAddress = new ReceiverAddress();
        receiverAddress.setIsDefault(0);
        QueryWrapper<ReceiverAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return receiverAddressMapper.update(receiverAddress, queryWrapper);
    }

    @Override
    public ReceiverAddressStandardVO getStandardById(Long id) {
        log.debug("开始执行【根据ID查询收货地址数据详情】的数据访问，参数：{}", id);
        return receiverAddressMapper.getStandardById(id);
    }

    @Override
    public List<ReceiverAddressListItemVO> listByUser(Long userId) {
        log.debug("开始执行【根据用户查询收货地址列表】的数据访问，用户：{}", userId);
        return receiverAddressMapper.listByUser(userId);
    }

}
