package cn.tedu.tmall.admin.mall.dao.cache;

import cn.tedu.tmall.common.consts.cache.PassportCacheConsts;
import cn.tedu.tmall.common.po.UserStatePO;


public interface IUserCacheRepository extends PassportCacheConsts {

    // 向缓存中写入用户状态信息
    void saveUserState(Long id, UserStatePO userStatePO);

    // 从缓存中读取用户状态信息
    UserStatePO getUserState(Long id);

}
