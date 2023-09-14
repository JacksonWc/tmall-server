package cn.tedu.tmall.front.mall.service;


import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.front.mall.pojo.param.ReceiverAddressAddNewParam;
import cn.tedu.tmall.front.mall.pojo.param.ReceiverAddressUpdateParam;
import cn.tedu.tmall.front.mall.pojo.vo.ReceiverAddressListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.ReceiverAddressStandardVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 处理收货地址数据的业务接口
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Transactional
public interface IReceiverAddressService {

    /**
     * 添加收货地址
     *
     * @param currentPrincipal           当事人
     * @param receiverAddressAddNewParam 添加收货地址的参数类
     */
    void addNew(CurrentPrincipal currentPrincipal, ReceiverAddressAddNewParam receiverAddressAddNewParam);

    /**
     * 删除收货地址
     *
     * @param currentPrincipal 当事人
     * @param id               收货地址ID
     */
    void delete(CurrentPrincipal currentPrincipal, Long id);

    /**
     * 修改收货地址
     *
     * @param currentPrincipal           当事人
     * @param receiverAddressUpdateParam 封装了收货地址ID和新数据的对象
     */
    void updateInfoById(CurrentPrincipal currentPrincipal, Long id, ReceiverAddressUpdateParam receiverAddressUpdateParam);

    /**
     * 设置默认收货地址
     *
     * @param currentPrincipal 当事人
     * @param id               需要设置为默认状态的收货地址ID
     */
    void setDefault(CurrentPrincipal currentPrincipal, Long id);

    /**
     * 根据ID查询收货地址
     *
     * @param currentPrincipal 当事人
     * @param id               收货地址ID
     * @return 匹配的收货地址数据详情，如果没有匹配的数据，则返回null
     */
    ReceiverAddressStandardVO getStandardById(CurrentPrincipal currentPrincipal, Long id);

    /**
     * 查询收货地址列表
     *
     * @param currentPrincipal 当事人
     * @return 收货地址列表
     */
    List<ReceiverAddressListItemVO> listByUser(CurrentPrincipal currentPrincipal);

}
