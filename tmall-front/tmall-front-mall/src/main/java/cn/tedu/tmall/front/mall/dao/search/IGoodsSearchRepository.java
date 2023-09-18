package cn.tedu.tmall.front.mall.dao.search;


import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.front.mall.pojo.vo.GoodsSearchVO;

/**
 * 处理商品搜索数据的存储库接口
 *
 * @author java@tedu.cn
 * @version 3.0
 */
public interface IGoodsSearchRepository {

    /**
     * 搜索商品
     *
     * @param keyword  关键词
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return 搜索的分页结果
     */
    PageData<GoodsSearchVO> search(String keyword, Integer pageNum, Integer pageSize);

}
