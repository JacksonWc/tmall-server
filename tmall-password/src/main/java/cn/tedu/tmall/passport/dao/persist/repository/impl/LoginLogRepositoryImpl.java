package cn.tedu.tmall.passport.dao.persist.repository.impl;

import cn.tedu.tmall.passport.dao.persist.mapper.LoginLogMapper;
import cn.tedu.tmall.passport.dao.persist.repository.ILoginLogRepository;
import cn.tedu.tmall.passport.pojo.entity.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginLogRepositoryImpl implements ILoginLogRepository {
    @Autowired
    LoginLogMapper loginLogMapper;

    @Override
    public int insert(LoginLog loginLog) {
        return loginLogMapper.insert(loginLog);
    }
}
