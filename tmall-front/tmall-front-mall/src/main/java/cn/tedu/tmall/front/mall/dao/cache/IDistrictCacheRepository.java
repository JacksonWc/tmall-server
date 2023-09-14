package cn.tedu.tmall.front.mall.dao.cache;

import cn.tedu.tmall.common.consts.cache.DistrictCacheConsts;
import cn.tedu.tmall.common.po.DistrictSimplePO;


/**
 * 处理省市区数据的缓存访问接口
 *
 * @author java@tedu.cn
 * @version 2.0
 */
public interface IDistrictCacheRepository extends DistrictCacheConsts {

    /**
     * 根据地区的行政代码获取地区数据
     *
     * @param code 地区的行政代码
     * @return 地区数据
     */
    DistrictSimplePO getByCode(String code);

}
