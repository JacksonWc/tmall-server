package cn.tedu.tmall.passport.dao.persist.repository;

import cn.tedu.tmall.passport.pojo.entity.LoginLog;

public interface ILoginLogRepository {
    int insert(LoginLog loginLog);
}
