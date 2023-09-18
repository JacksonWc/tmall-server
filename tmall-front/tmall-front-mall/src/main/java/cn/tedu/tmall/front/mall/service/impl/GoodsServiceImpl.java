package cn.tedu.tmall.front.mall.service.impl;



import cn.tedu.tmall.common.enumerator.ServiceCode;
import cn.tedu.tmall.common.ex.ServiceException;
import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.front.mall.dao.persist.repository.IGoodsRepository;
import cn.tedu.tmall.front.mall.dao.search.IGoodsSearchRepository;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsListItemVO;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsSearchVO;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsStandardVO;
import cn.tedu.tmall.front.mall.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 处理商品数据的业务实现类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Value("${tmall.dao.default-query-page-size}")
    private Integer defaultQueryPageSize;
    @Autowired
    private IGoodsRepository goodsRepository;

    public GoodsServiceImpl() {
        log.debug("创建业务类对象：GoodsServiceImpl");
    }

    @Autowired
    private IGoodsSearchRepository goodsSearchRepository;


    @Override
    public GoodsStandardVO getStandardById(Long id) {
        log.debug("开始处理【根据ID查询商品】的业务，参数：{}", id);
    GoodsStandardVO queryResult = goodsRepository.getStandardById(id);
        log.trace("查询结果：{}", queryResult);
        if (queryResult == null) {
            String message = "查询商品详情失败，商品数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }
        return queryResult;
    }

    @Override
    public PageData<GoodsListItemVO> listByRecommend(Integer pageNum) {
        log.debug("开始处理【查询推荐的商品列表】的业务，页码：{}", pageNum);
        return goodsRepository.listByRecommend(pageNum, defaultQueryPageSize);
    }

    @Override
    public PageData<GoodsListItemVO> listByCategory(Long categoryId, Integer pageNum) {
        log.debug("开始处理【根据类别查询商品列表】的业务，商品类别：{}, 页码：{}", categoryId, pageNum);
        return goodsRepository.listByCategory(categoryId, pageNum, defaultQueryPageSize);
    }

    @Override
    public PageData<GoodsSearchVO> search(String keyword, Integer pageNum) {
        log.debug("开始处理【搜索商品】的业务，关键词：{}, 页码：{}，每页记录数：{}", keyword, pageNum);
        return goodsSearchRepository.search(keyword, pageNum, defaultQueryPageSize);
    }

    @Override
    public PageData<GoodsSearchVO> search(String keyword, Integer pageNum, Integer pageSize) {
        log.debug("开始处理【搜索商品】的业务，关键词：{}, 页码：{}，每页记录数：{}", keyword, pageNum, pageSize);
        return goodsSearchRepository.search(keyword, pageNum, pageSize);
    }
}
