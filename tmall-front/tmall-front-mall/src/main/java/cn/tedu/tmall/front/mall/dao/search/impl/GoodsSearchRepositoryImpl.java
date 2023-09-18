package cn.tedu.tmall.front.mall.dao.search.impl;


import cn.tedu.tmall.common.po.GoodsSearchPO;
import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.front.mall.dao.search.IGoodsSearchRepository;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsSearchVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理商品搜索数据的存储库实现类
 *
 * @author java@tedu.cn
 * @version 3.0
 */
@Repository
public class GoodsSearchRepositoryImpl implements IGoodsSearchRepository {

    @Autowired
    private GoodsElasticsearchRepositoryImpl goodsElasticsearchRepository;

    @Override
    public PageData<GoodsSearchVO> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        SearchPage<GoodsSearchPO> searchPage = goodsElasticsearchRepository.customSearch(keyword, pageable);
        return convertSearchPageToPageData(searchPage);
    }

    private PageData<GoodsSearchVO> convertSearchPageToPageData(SearchPage<GoodsSearchPO> searchPage) {
        SearchHits<GoodsSearchPO> searchHits = searchPage.getSearchHits();
        List<GoodsSearchVO> goodsSearchList = new ArrayList<>();
        for (SearchHit<GoodsSearchPO> searchHit : searchHits) {
            goodsSearchList.add(convertSearchHitToVO(searchHit));
        }

        PageData<GoodsSearchVO> pageData = new PageData<>();
        pageData.setCurrentPage(searchPage.getNumber());
        pageData.setPageSize(searchPage.getSize());
        pageData.setMaxPage(searchPage.getTotalPages());
        pageData.setTotal(searchHits.getTotalHits());
        pageData.setList(goodsSearchList);
        return pageData;
    }

    private GoodsSearchVO convertSearchHitToVO(SearchHit<GoodsSearchPO> searchHit) {
        GoodsSearchPO goodsSearchPO = searchHit.getContent();
        List<String> highlightFields = searchHit.getHighlightField("title");
        GoodsSearchVO goodsSearchVO = new GoodsSearchVO();
        BeanUtils.copyProperties(goodsSearchPO, goodsSearchVO);
        if (highlightFields.size() > 0) {
            goodsSearchVO.setTitle(highlightFields.get(0));
        }
        return goodsSearchVO;
    }

}
