package cn.tedu.tmall.front.mall.service.impl;

import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.common.enumerator.ServiceCode;
import cn.tedu.tmall.common.ex.ServiceException;
import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.front.mall.dao.cache.ICartCacheRepository;
import cn.tedu.tmall.front.mall.dao.persist.repository.IGoodsRepository;
import cn.tedu.tmall.front.mall.dao.persist.repository.IOrderItemRepository;
import cn.tedu.tmall.front.mall.dao.persist.repository.IOrderRepository;
import cn.tedu.tmall.front.mall.dao.persist.repository.IReceiverAddressRepository;
import cn.tedu.tmall.front.mall.pojo.entity.Order;
import cn.tedu.tmall.front.mall.pojo.entity.OrderItem;
import cn.tedu.tmall.front.mall.pojo.param.OrderAddNewParam;
import cn.tedu.tmall.front.mall.pojo.po.CartPO;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsStandardVO;
import cn.tedu.tmall.front.mall.pojo.vo.OrderListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.OrderStandardVO;
import cn.tedu.tmall.front.mall.pojo.vo.ReceiverAddressStandardVO;
import cn.tedu.tmall.front.mall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 处理订单数据的业务实现类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Value("${tmall.dao.default-query-page-size}")
    private Integer defaultQueryPageSize;
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderItemRepository orderItemRepository;
    @Autowired
    private IReceiverAddressRepository receiverAddressRepository;
    @Autowired
    private IGoodsRepository goodsRepository;
    @Autowired
    private ICartCacheRepository cartCacheRepository;

    public OrderServiceImpl() {
        log.debug("创建业务类对象：OrderServiceImpl");
    }

    @Override
    public Long create(CurrentPrincipal currentPrincipal, OrderAddNewParam orderAddNewParam) {
        log.debug("开始处理【创建订单】的业务，当事人：{}，参数：{}", currentPrincipal, orderAddNewParam);
        Long receiverAddressId = orderAddNewParam.getReceiverAddressId();
        //这是要得到的内容的前置条件
        ReceiverAddressStandardVO receiverAddress = receiverAddressRepository.getStandardById(receiverAddressId);
        if (receiverAddress == null) {
            String message = "创建订单失败，收货地址数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        //这是要得到的，现在的效果是地址是收货地址要改就改全部
        String fullAddress = receiverAddress.getProvince() + receiverAddress.getCity() + receiverAddress.getArea() + receiverAddress.getDetail();

        Long[] goodsIdList = orderAddNewParam.getGoodsIdList();

        BigDecimal totalPrice = BigDecimal.ZERO;
        Integer goodsTotalNum = 0;

        List<GoodsStandardVO> goodsList = new ArrayList<>();
        List<Integer> goodsNumList = new ArrayList<>();
        for (int i = 0; i < goodsIdList.length; i++) {
            GoodsStandardVO goods = goodsRepository.getStandardById(goodsIdList[i]);
            if (goods == null) {
                String message = "创建订单失败，部分商品数据不存在！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
            }
            if (goods.getIsPutOn() != 1) {
                String message = "创建订单失败，商品【" + goods.getTitle() + "】已下架！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
            }
            goodsList.add(goods);

            CartPO cartPO = cartCacheRepository.getByUserAndGoods(currentPrincipal.getId(), goodsIdList[i]);
            Integer goodsNum = cartPO.getGoodsNum();
            goodsNumList.add(goodsNum);

            //这是要得到的
            goodsTotalNum += goodsNum;

            BigDecimal multiply = goods.getSalePrice().multiply(new BigDecimal(goodsNum));
            //这是要得到的
            totalPrice = totalPrice.add(multiply);
        }

        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setBuyerId(currentPrincipal.getId());
        order.setBuyerUsername(currentPrincipal.getUsername());
        order.setReceiverName(receiverAddress.getReceiverName());
        order.setReceiverPhone(receiverAddress.getReceiverPhone());
        order.setReceiverAddress(fullAddress);
        order.setGoodsNum(goodsTotalNum);
        order.setTotalPrice(totalPrice);
        order.setOrderState(ORDER_STATE_UNPAID);
        int rows = orderRepository.insert(order);
        if (rows != 1) {
            String message = "创建订单失败，服务器忙，请稍后再试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }

        //订单创建好之后，在mall_order_item表中也要更新数据
        for (int i = 0; i < goodsList.size(); i++) {
            GoodsStandardVO goods = goodsList.get(i);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setGoodsId(goods.getId());
            orderItem.setGoodsCoverUrl(goods.getCoverUrl());
            orderItem.setGoodsTitle(goods.getTitle());
            orderItem.setGoodsNum(goodsNumList.get(i));
            orderItem.setSaleUnitPrice(goods.getSalePrice());
            rows = orderItemRepository.insert(orderItem);
            if (rows != 1) {
                String message = "创建订单失败，服务器忙，请稍后再试！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_INSERT, message);
            }
        }

        //创建订单后购物车对应的数据要清空
        for (int i = 0; i < goodsIdList.length; i++) {
            cartCacheRepository.deleteByUserAndGoods(currentPrincipal.getId(), goodsIdList[i]);
        }

        return order.getId();
    }

    @Override
    public OrderStandardVO getStandardById(CurrentPrincipal currentPrincipal, Long orderId) {
        log.debug("开始处理【根据ID查询订单】的业务，当事人：{}，参数：{}", currentPrincipal, orderId);
        OrderStandardVO order = orderRepository.getStandardByIdAndUser(orderId, currentPrincipal.getId());
        if (order == null) {
            String message = "查询订单失败，尝试访问的数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        return order;
    }

    @Override
    public PageData<OrderListItemVO> listByUser(CurrentPrincipal currentPrincipal, Integer pageNum) {
        log.debug("开始处理【查询订单列表】的业务，用当事人户：{}, 页码：{}", currentPrincipal, pageNum);
        return orderRepository.listByUser(currentPrincipal.getId(), pageNum, defaultQueryPageSize);
    }

    @Override
    public PageData<OrderListItemVO> listByUser(CurrentPrincipal currentPrincipal, Integer pageNum, Integer pageSize) {
        log.debug("开始处理【查询订单列表】的业务，当事人：{}, 页码：{}，每页记录数：{}", currentPrincipal, pageNum, pageSize);
        return orderRepository.listByUser(currentPrincipal.getId(), pageNum, pageSize);
    }

}
