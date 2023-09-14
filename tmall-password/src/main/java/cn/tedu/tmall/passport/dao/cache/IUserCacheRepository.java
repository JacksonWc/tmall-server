package cn.tedu.tmall.passport.dao.cache;


import cn.tedu.tmall.common.cache.PassportCacheConsts;
import cn.tedu.tmall.common.po.UserStatePO;

public interface IUserCacheRepository extends PassportCacheConsts {

    // 向缓存中写入用户状态信息
    void saveUserState(Long id, UserStatePO userStatePO);

    // 从缓存中读取用户状态信息
    UserStatePO getUserState(Long id);

    // 删除用户状态信息
    boolean deleteUserState(Long id);

    // 续期用户状态信息
    void expire(Long id);
}
