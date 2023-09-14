package cn.tedu.tmall.passport.controller;


import cn.tedu.tmall.common.authentication.CurrentPrincipal;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/test")
@Api(tags = "测试访问")
public class TestController {

    @GetMapping("/permit")
    @ApiOperation("不需要通过认证，就是在permit的里面的")
    @ApiOperationSupport(order = 100)
    public String permit() {
        return "成功处理请求：不需要通过认证的请求";
    }

    @GetMapping("/authenticated")
    @ApiOperation("需要通过认证")
    @ApiOperationSupport(order = 110)
    public String authenticated() {
        return "成功处理请求：需要通过认证的请求";
    }

    @GetMapping("/principal")
    @ApiOperation("识别当事人")
    @ApiOperationSupport(order = 120)
    public String principal(@ApiIgnore @AuthenticationPrincipal CurrentPrincipal principal) {
        return "成功处理请求：需要通过认证，且识别当事人：" + principal;
    }

    @GetMapping("/create-account")
//    在Controller的方法之前添加注解来配置权限
    @PreAuthorize("hasAuthority('/account/user/query')")
    @ApiOperation("需要具有权限")
    @ApiOperationSupport(order = 130)
    public String hasAuthority() {
        return "成功处理请求：需要具有某个权限";
    }

}
