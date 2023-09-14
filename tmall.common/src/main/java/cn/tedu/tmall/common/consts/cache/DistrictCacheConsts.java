package cn.tedu.tmall.common.consts.cache;

/**
 * 地区数据缓存相关常量
 *
 * @author java@tedu.cn
 * @version 2.0
 */
public interface DistrictCacheConsts {

    /**
     * 缓存数据的KEY的前缀：根据父级存储的地区列表
     */
    String KEY_PREFIX_LIST_BY_PARENT = "district:list-by-parent:";

    /**
     * 缓存数据的KEY的前缀：地区数据
     */
    String KEY_PREFIX_ITEM = "district:item:";

    /**
     * 缓存中所有列表数据的Key集合的Key
     */
    String KEY_ALL_KEYS = "district:keys";

}
