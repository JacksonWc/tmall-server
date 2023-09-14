package cn.tedu.tmall.passport.dao.persist.repository;

import cn.tedu.tmall.passport.pojo.vo.UserLoginInfoVO;

import java.time.LocalDateTime;

public interface IUserRepository {

    UserLoginInfoVO getLoginInfoByUsername(String username);

    int updateLastLogin(Long id, Integer totalLoginCount,String remoteAddr, LocalDateTime now);



}
