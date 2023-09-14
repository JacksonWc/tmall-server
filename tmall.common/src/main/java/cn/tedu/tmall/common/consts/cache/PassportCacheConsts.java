package cn.tedu.tmall.common.consts.cache;

/**
 * 单点登录缓存相关常量
 *
 * @author java@tedu.cn
 * @version 2.0
 */
public interface PassportCacheConsts {

    /**
     * 用户状态信息的Key前缀
     */
    String KEY_PREFIX_USER_STATE = "passport:user-state:";

    /**
     * 用户状态的Hash对象中“启用状态”的Key
     */
    String HASH_KEY_USER_ENABLE = "enable";

}
