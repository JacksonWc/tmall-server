package cn.tedu.tmall.front.mall.service.impl;

import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.common.enumerator.ServiceCode;
import cn.tedu.tmall.common.ex.ServiceException;
import cn.tedu.tmall.common.po.DistrictSimplePO;
import cn.tedu.tmall.front.mall.dao.cache.IDistrictCacheRepository;
import cn.tedu.tmall.front.mall.dao.persist.repository.IReceiverAddressRepository;
import cn.tedu.tmall.front.mall.pojo.entity.ReceiverAddress;
import cn.tedu.tmall.front.mall.pojo.param.ReceiverAddressAddNewParam;
import cn.tedu.tmall.front.mall.pojo.param.ReceiverAddressUpdateParam;
import cn.tedu.tmall.front.mall.pojo.vo.ReceiverAddressListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.ReceiverAddressStandardVO;
import cn.tedu.tmall.front.mall.service.IReceiverAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 处理收货地址数据的业务实现类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@Service
public class ReceiverAddressServiceImpl implements IReceiverAddressService {

    @Autowired
    private IReceiverAddressRepository receiverAddressRepository;
    @Autowired
    private IDistrictCacheRepository districtCacheRepository;

    public ReceiverAddressServiceImpl() {
        log.debug("创建业务类对象：ReceiverAddressServiceImpl");
    }

    @Override
    public void addNew(CurrentPrincipal currentPrincipal, ReceiverAddressAddNewParam receiverAddressAddNewParam) {
        log.debug("开始处理【添加收货地址】的业务，当事人：{}，参数：{}", currentPrincipal, receiverAddressAddNewParam);

        //通过前端传的code查询得到省和市的DistrictSimplePO
        DistrictSimplePO province = districtCacheRepository.getByCode(receiverAddressAddNewParam.getProvinceCode());
        DistrictSimplePO city = districtCacheRepository.getByCode(receiverAddressAddNewParam.getCityCode());
        if (province == null || city == null) {
            String message = "添加收货地址失败，选择的地区数据有误！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }


        log.info("开始创建收货地址对象，然后要做赋值了");
        ReceiverAddress receiverAddress = new ReceiverAddress();
        BeanUtils.copyProperties(receiverAddressAddNewParam, receiverAddress);
        log.info("把receiverAddressAddNewParam属性赋值给receiverAddress的结果是{}",receiverAddress);


        receiverAddress.setUserId(currentPrincipal.getId());
        receiverAddress.setProvince(province.getName());
        receiverAddress.setCity(city.getName());
          //因为不是所有的市下面都有区县的信息，所以区的信息要单独判断单独存
        if (StringUtils.hasText(receiverAddressAddNewParam.getAreaCode())) {
            DistrictSimplePO area = districtCacheRepository.getByCode(receiverAddressAddNewParam.getAreaCode());
            receiverAddress.setArea(area.getName());
        }

//        如果把当前新增的地址配置成默认地址，那把其他的地址设置为非默认
        Integer isDefault = receiverAddressAddNewParam.getIsDefault();
        if (isDefault == 1) {
            receiverAddressRepository.updateNonDefaultByUser(currentPrincipal.getId());
        }

        //向mysql插入收货地址
        int rows = receiverAddressRepository.insert(receiverAddress);
        if (rows != 1) {
            String message = "添加收货地址失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }
    }

    @Override
    public void delete(CurrentPrincipal currentPrincipal, Long id) {
        log.debug("开始处理【删除收货地址】的业务，当事人：{}，参数：{}", currentPrincipal, id);
        ReceiverAddressStandardVO queryResult = receiverAddressRepository.getStandardById(id);
        if (queryResult == null) {
            String message = "删除收货地址失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        if (!queryResult.getUserId().equals(currentPrincipal.getId())) {
            String message = "删除收货地址失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        int rows = receiverAddressRepository.deleteById(id);
        if (rows != 1) {
            String message = "删除收货地址失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_DELETE, message);
        }
    }

    @Override
    public void updateInfoById(CurrentPrincipal currentPrincipal, Long id, ReceiverAddressUpdateParam receiverAddressUpdateParam) {
        log.debug("开始处理【修改收货地址】的业务，当事人：{}，ID：{}，新数据：{}", currentPrincipal, id, receiverAddressUpdateParam);
        DistrictSimplePO province = districtCacheRepository.getByCode(receiverAddressUpdateParam.getProvinceCode());
        DistrictSimplePO city = districtCacheRepository.getByCode(receiverAddressUpdateParam.getCityCode());
        if (province == null || city == null) {
            String message = "修改收货地址失败，选择的地区数据有误！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        ReceiverAddressStandardVO queryResult = receiverAddressRepository.getStandardById(id);
        if (queryResult == null) {
            String message = "修改收货地址失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        if (!queryResult.getUserId().equals(currentPrincipal.getId())) {
            String message = "修改收货地址失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        ReceiverAddress receiverAddress = new ReceiverAddress();
        BeanUtils.copyProperties(receiverAddressUpdateParam, receiverAddress);
        receiverAddress.setId(id);
        receiverAddress.setProvince(province.getName());
        receiverAddress.setCity(city.getName());
        if (StringUtils.hasText(receiverAddressUpdateParam.getAreaCode())) {
            DistrictSimplePO area = districtCacheRepository.getByCode(receiverAddressUpdateParam.getAreaCode());
            receiverAddress.setArea(area.getName());
        }

        int rows = receiverAddressRepository.update(receiverAddress);
        if (rows != 1) {
            String message = "修改收货地址失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_DELETE, message);
        }
    }

    @Override
    public void setDefault(CurrentPrincipal currentPrincipal, Long id) {
        log.debug("开始处理【设置默认收货地址】的业务，当事人：{}，ID：{}", currentPrincipal, id);
        ReceiverAddressStandardVO queryResult = receiverAddressRepository.getStandardById(id);
        if (queryResult == null) {
            String message = "设置默认收货地址失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        if (!queryResult.getUserId().equals(currentPrincipal.getId())) {
            String message = "设置默认收货地址失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        int rows = receiverAddressRepository.updateNonDefaultByUser(currentPrincipal.getId());
        if (rows < 1) {
            String message = "设置默认收货地址失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_DELETE, message);
        }

        ReceiverAddress receiverAddress = new ReceiverAddress();
        receiverAddress.setId(id);
        receiverAddress.setIsDefault(1);
        rows = receiverAddressRepository.update(receiverAddress);
        if (rows != 1) {
            String message = "设置默认收货地址失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_DELETE, message);
        }
    }

    @Override
    public ReceiverAddressStandardVO getStandardById(CurrentPrincipal currentPrincipal, Long id) {
        log.debug("开始处理【根据ID查询收货地址】的业务，当事人：{}，参数：{}", currentPrincipal, id);
        ReceiverAddressStandardVO queryResult = receiverAddressRepository.getStandardById(id);
        if (queryResult == null) {
            String message = "查询收货地址失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        //如果查询的不是当前用户的数据，抛出异常；或者可以把两个参数都传到dao层再数据库用sql进行查询
        if (!queryResult.getUserId().equals(currentPrincipal.getId())) {
            String message = "查询收货地址失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        return queryResult;
    }

    @Override
    public List<ReceiverAddressListItemVO> listByUser(CurrentPrincipal currentPrincipal) {
        log.debug("开始处理【查询收货地址列表】的业务，当事人：{}", currentPrincipal);
        return receiverAddressRepository.listByUser(currentPrincipal.getId());
    }

}
