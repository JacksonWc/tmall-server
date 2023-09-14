package cn.tedu.tmall.passport.service;

import cn.tedu.tmall.passport.pojo.param.UserLoginInfoParam;
import cn.tedu.tmall.passport.pojo.vo.UserLoginResultVO;
import cn.tedu.tmall.common.authentication.CurrentPrincipal;

public interface IUserService {

    UserLoginResultVO login(UserLoginInfoParam userLoginInfoParam, String remoteAddr, String userAgent);

    void logout(CurrentPrincipal currentPrincipal);
}
