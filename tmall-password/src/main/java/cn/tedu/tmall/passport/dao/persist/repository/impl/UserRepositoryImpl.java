package cn.tedu.tmall.passport.dao.persist.repository.impl;

import cn.tedu.tmall.passport.dao.persist.mapper.UserMapper;
import cn.tedu.tmall.passport.dao.persist.repository.IUserRepository;
import cn.tedu.tmall.passport.pojo.entity.User;
import cn.tedu.tmall.passport.pojo.vo.UserLoginInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    @Value("${tmall.jwt.duration-in-minute}")
    private long durationInMinute;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserLoginInfoVO getLoginInfoByUsername(String username) {
        return userMapper.getLoginInfoByUsername(username);
    }

    @Override
    public int updateLastLogin(Long id, Integer totalLoginCount,String remoteAddr, LocalDateTime now) {
        User user=new User();
        user.setId(id);
        user.setLoginCount(totalLoginCount);
        user.setLastLoginIp(remoteAddr);
        user.setGmtLastLogin(now);
        return userMapper.updateById(user);
    }



}
