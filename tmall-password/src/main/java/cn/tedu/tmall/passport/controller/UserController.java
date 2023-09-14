package cn.tedu.tmall.passport.controller;


import cn.tedu.tmall.passport.pojo.param.UserLoginInfoParam;
import cn.tedu.tmall.passport.pojo.vo.UserLoginResultVO;
import cn.tedu.tmall.passport.service.IUserService;
import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import cn.tedu.tmall.common.web.JsonResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/passport")
@Api(tags = "单点登录")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation("用户登录")
    @ApiOperationSupport(order = 100)
    @PostMapping("/login")
    public JsonResult login(@Valid UserLoginInfoParam userLoginInfoParam, @ApiIgnore HttpServletRequest request) {

        String remoteAddr = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        UserLoginResultVO loginResult = userService.login(userLoginInfoParam, remoteAddr, userAgent);
        return JsonResult.ok(loginResult);
    }

    @ApiOperation("退出登录")
    @ApiOperationSupport(order = 200)
    @PostMapping("/logout")
    public JsonResult logout(@ApiIgnore @AuthenticationPrincipal CurrentPrincipal currentPrincipal) {
        userService.logout(currentPrincipal);
        return JsonResult.ok();
    }

}
