package cn.tedu.tmall.admin.mall.service.impl;

import cn.tedu.tmall.admin.mall.dao.persist.repository.ICategoryRepository;
import cn.tedu.tmall.admin.mall.dao.persist.repository.IGoodsDetailRepository;
import cn.tedu.tmall.admin.mall.dao.persist.repository.IGoodsRepository;
import cn.tedu.tmall.admin.mall.dao.persist.repository.impl.GoodsRepositoryImpl;
import cn.tedu.tmall.admin.mall.pojo.entity.Goods;
import cn.tedu.tmall.admin.mall.pojo.entity.GoodsDetail;
import cn.tedu.tmall.admin.mall.pojo.param.GoodsAddNewParam;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryStandardVO;
import cn.tedu.tmall.admin.mall.pojo.vo.GoodsListItemVO;
import cn.tedu.tmall.admin.mall.pojo.vo.GoodsStandardVO;
import cn.tedu.tmall.admin.mall.service.IGoodsService;
import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.common.enumerator.ServiceCode;
import cn.tedu.tmall.common.ex.ServiceException;
import cn.tedu.tmall.common.vo.PageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoodServiceImpl implements IGoodsService {
    @Autowired
    IGoodsRepository goodsRepository;
    @Autowired
    ICategoryRepository categoryRepository;
    @Autowired
    IGoodsDetailRepository goodsDetailRepository;

    @Value("${tmall.dao.default-query-page-size}")
    private Integer defaultQueryPageSize;

    @Override
    public void getNewGoods(CurrentPrincipal currentPrincipal, String remoteAddr, GoodsAddNewParam goodsAddNewParam) {

        log.debug("开始处理【发布商品】的业务，当事人：{}，IP地址：{}，参数：{}", currentPrincipal, remoteAddr, goodsAddNewParam);

        Long categoryId = goodsAddNewParam.getCategoryId();
        CategoryStandardVO category = categoryRepository.getStandardById(categoryId);
        if (category == null) {
            String message = "发布商品失败，类别数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        //不能把商品挂在父级类别下
        if (category.getIsParent() != 0) {
            String message = "发布商品失败，选择的类别必须不包含子级类别！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        if (category.getEnable() != 1) {
            String message = "发布商品失败，选择的类别已经被禁用！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsAddNewParam, goods);
        goods.setCategoryName(category.getName());
        goods.setCheckState(0);
        goods.setIsRecommend(0);
        goods.setIsPutOn(0);
        goods.setSalesCount(0);
        goods.setCommentCount(0);
        goods.setPositiveCommentCount(0);
        goods.setNegativeCommentCount(0);
        int rows = goodsRepository.insert(goods);
        if (rows != 1) {
            String message = "发布商品失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }

        GoodsDetail goodsDetail = new GoodsDetail();
        goodsDetail.setGoodsId(goods.getId());
        goodsDetail.setDetail(goodsAddNewParam.getDetail());
        rows = goodsDetailRepository.insert(goodsDetail);
        if (rows != 1) {
            String message = "发布商品失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }
//        下面是之前的写法
        //        Goods goods=new Goods();
//        BeanUtils.copyProperties(goodsAddNewParam,goods);
//        //TODO 需要验证的字段


        // 需要服务器设置的参数，id，商品分类名称，是否推荐，审核状态，
        // 是否上架，销量，评论数，好评数，差评数，2个时间

        //设置商品分类名称
        //通过商品分类的id查询商品分类名称，联合mall_goods表
        //解决的bug-1就查不了（查不了但是能插入成功--异常被捕获了等于不存在），8就能查--问题在于查询的string应该来自于category表而不是goods表
//       String categoryName= goodsRepository.getCategoryName(goodsAddNewParam.getCategoryId());
//       goods.setCategoryName(categoryName);
//       log.error(categoryName);
        //其他按着默认初始的来设置,数据库里面有设置了默认值了，这里可以不写
        /**
         //默认不推荐
         goods.setIsRecommend(0);
         //默认未审核
         goods.setCheckState(0);
         //默认不上架
         goods.setIsPutOn(0);
         //销量默认为0
         goods.setSalesCount(0);
         //评论数默认为0
         goods.setCommentCount(0);
         //好评数默认为0
         goods.setPositiveCommentCount(0);
         //差评数默认为0
         goods.setNegativeCommentCount(0);
         **/

//        return goodsRepository.getNewGoods(goods);
    }

    @Override
    public PageData<GoodsListItemVO> lisAllGoods(Integer pageNum) {

        return goodsRepository.listAllGoods(pageNum, defaultQueryPageSize);
    }

    @Override
    public GoodsStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据ID查询商品】的业务，参数：{}", id);
        GoodsStandardVO queryResult = goodsRepository.getStandardById(id);
        if (queryResult == null) {
            String message = "查询商品失败，商品数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        return queryResult;
    }

    @Override
    public PageData<GoodsListItemVO> listByCategory(Long categoryId, Integer pageNum) {
        log.debug("开始处理【根据类别查询商品列表】的业务，商品类别：{}, 页码：{}", categoryId, pageNum);
        return goodsRepository.listByCategory(categoryId, pageNum, defaultQueryPageSize);
    }


}
