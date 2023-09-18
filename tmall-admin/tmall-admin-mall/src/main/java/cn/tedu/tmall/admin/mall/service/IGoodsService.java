package cn.tedu.tmall.admin.mall.service;

import cn.tedu.tmall.admin.mall.pojo.param.GoodsAddNewParam;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.admin.mall.pojo.vo.GoodsListItemVO;
import cn.tedu.tmall.admin.mall.pojo.vo.GoodsStandardVO;
import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.common.vo.PageData;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IGoodsService {

    void getNewGoods(CurrentPrincipal currentPrincipal, String remoteAddr, GoodsAddNewParam goodsAddNewParam);

    PageData<GoodsListItemVO> lisAllGoods(Integer pageNum);

    GoodsStandardVO getStandardById(Long id);

    PageData<GoodsListItemVO> listByCategory(Long categoryId, Integer pageNum);

    /**
     * 重建商品的搜索数据（更新ES中的商品数据）
     */
    void rebuildSearch();
}
