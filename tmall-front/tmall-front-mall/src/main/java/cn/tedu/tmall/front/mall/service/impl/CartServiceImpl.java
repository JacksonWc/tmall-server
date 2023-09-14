package cn.tedu.tmall.front.mall.service.impl;

import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.common.enumerator.ServiceCode;
import cn.tedu.tmall.common.ex.ServiceException;
import cn.tedu.tmall.front.mall.converter.GoodsToCartConverter;
import cn.tedu.tmall.front.mall.dao.cache.ICartCacheRepository;
import cn.tedu.tmall.front.mall.dao.persist.repository.IGoodsRepository;
import cn.tedu.tmall.front.mall.pojo.po.CartPO;
import cn.tedu.tmall.front.mall.pojo.vo.CartListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsStandardVO;
import cn.tedu.tmall.front.mall.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理购物车的业务实现类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private ICartCacheRepository cartCacheRepository;
    @Autowired
    private IGoodsRepository goodsRepository;

    public CartServiceImpl() {
        log.debug("创建业务类对象：CartServiceImpl");
    }

    @Override
    public void add(CurrentPrincipal currentPrincipal, Long goodsId, Integer goodsNum) {
        log.debug("开始处理【添加商品到购物车】的业务，当事人：{}，商品：{}，数量：{}", currentPrincipal, goodsId, goodsNum);
        GoodsStandardVO goods = goodsRepository.getStandardById(goodsId);
        if (goods == null) {
            String message = "添加购物车失败，商品数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        if (goods.getIsPutOn() != 1) {
            String message = "添加购物车失败，商品已下架！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        CartPO cartPO = new CartPO(goodsId, goodsNum);
        //根据用户与商品查询购物车中的数据,如果已经有这个商品了，数量就在原来的基础上加上现在增加的
        CartPO cartCache = cartCacheRepository.getByUserAndGoods(currentPrincipal.getId(), goodsId);
        if (cartCache != null) {
            cartPO.setGoodsNum(cartCache.getGoodsNum() + goodsNum);
        }
        //加购物车
        cartCacheRepository.put(currentPrincipal.getId(), cartPO);
    }

    @Override
    public void delete(CurrentPrincipal currentPrincipal, Long goodsId) {
        log.debug("开始处理【删除购物车中的商品】的业务，当事人：{}，商品：{}", currentPrincipal, goodsId);
        cartCacheRepository.deleteByUserAndGoods(currentPrincipal.getId(), goodsId);
    }

    @Override
    public Integer increaseNum(CurrentPrincipal currentPrincipal, Long goodsId) {
        log.debug("开始处理【将购物车中商品的数量增加1】的业务，当事人：{}，商品：{}", currentPrincipal, goodsId);
        return increaseNum(currentPrincipal.getId(), goodsId, 1);
    }

    @Override
    public Integer increaseNum(CurrentPrincipal currentPrincipal, Long goodsId, Integer num) {
        log.debug("开始处理【增加购物车中商品的数量】的业务，当事人：{}，商品：{}，数量：{}", currentPrincipal, goodsId, num);
        return increaseNum(currentPrincipal.getId(), goodsId, num);
    }

    @Override
    public Integer reduceNum(CurrentPrincipal currentPrincipal, Long goodsId) {
        log.debug("开始处理【将购物车中商品的数量减少1】的业务，当事人：{}，商品：{}", currentPrincipal, goodsId);
        return reduceNum(currentPrincipal.getId(), goodsId, 1);
    }

    @Override
    public Integer reduceNum(CurrentPrincipal currentPrincipal, Long goodsId, Integer num) {
        log.debug("开始处理【减少购物车中商品的数量】的业务，当事人：{}，商品：{}，数量：{}", currentPrincipal, goodsId, num);
        return reduceNum(currentPrincipal.getId(), goodsId, num);
    }

    @Override
    public List<CartListItemVO> list(CurrentPrincipal currentPrincipal) {
        log.debug("开始处理【查询购物车列表】的业务，当事人：{}", currentPrincipal);
        List<CartListItemVO> cartList = new ArrayList<>();
        List<CartPO> cartPOList = cartCacheRepository.listByUser(currentPrincipal.getId());
        for (CartPO cartPO : cartPOList) {
            GoodsStandardVO goods = goodsRepository.getStandardById(cartPO.getGoodsId());
            if (goods != null) {
                CartListItemVO cartListItemVO = GoodsToCartConverter.convertToCart(cartPO, goods);
                cartList.add(cartListItemVO);
            }
        }
        return cartList;
    }

    private Integer increaseNum(Long userId, Long goodsId, Integer num) {
        CartPO cartCache = cartCacheRepository.getByUserAndGoods(userId, goodsId);
        Integer newNum = cartCache.getGoodsNum() + num;
        CartPO cartPO = new CartPO(goodsId, newNum);
        cartCacheRepository.put(userId, cartPO);
        return newNum;
    }

    private Integer reduceNum(Long userId, Long goodsId, Integer num) {
        CartPO cartCache = cartCacheRepository.getByUserAndGoods(userId, goodsId);
        if (cartCache == null) {
            return 0;
        }

        Integer newNum = cartCache.getGoodsNum() - num;
        if (newNum <= 0) {
            cartCacheRepository.deleteByUserAndGoods(userId, goodsId);
            return 0;

        }

        CartPO cartPO = new CartPO(goodsId, newNum);
        cartCacheRepository.put(userId, cartPO);
        return newNum;
    }

}