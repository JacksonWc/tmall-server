package cn.tedu.tmall.passport.config;


import cn.tedu.tmall.passport.filter.JwtAuthorizationFilter;
import com.alibaba.fastjson.JSON;
import cn.tedu.tmall.common.enumerator.ServiceCode;
import cn.tedu.tmall.common.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

@Configuration
//在Spring Security的配置类上开启“基于方法的访问控制”
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 配置Spring Security允许跨域访问（允许客户端提交复杂请求）
        // 会启用Spring Security自带的CorsFilter，此过滤器将会对OPTIONS类型的请求放行
        http.cors();

        // 处理未通过认证时的错误
        http.exceptionHandling().authenticationEntryPoint((request, response, e) -> {
            String message = "您当前未登录，请先登录！";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED, message);
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
            return;
        });


        // 当使用JWT后，可以配置Spring Security不再使用Session
        // SessionCreationPolicy.NEVER：从不主动创建Session，但是，如果Session已经存在，仍会自动使用Session
        // SessionCreationPolicy.STATELESS：无状态的，无论是否存在Session，都不会使用Session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 将解析JWT的过滤器添加到Spring Security的过滤器链中比较早期执行的位置
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        // 关闭“防止伪造跨域攻击的防御机制”
        http.csrf().disable();

        // 白名单，使用AntStyle模式
        // 使用1个星号，通配某个层级下的任意名称或资源，例如：/admin/* 可以通配：/admin/list或/admin/add-new，但不可以匹配到：/admin/9527/delete
        // 使用2个星号，通配若干层级下的任意名称或资源，例如：/admin/** 可以通配：/admin/list或/admin/add-new，也可以匹配到：/admin/9527/delete
        String[] urls = {
                "/doc.html",
                "/**/*.css",
                "/**/*.js",
                "/swagger-resources",
                "/v2/api-docs",
                "/passport/login",
                "/test/permit",
        };

        // 配置请求授权：采用第一匹配原则
        http.authorizeRequests() // 开始配置请求授权
                .mvcMatchers(urls) // 匹配某些请求
                .permitAll() // 直接许可，即：不需要通过认证
                .anyRequest() // 任意请求（所有请求），表现为：除了以上配置的以外的所有请求
                .authenticated(); // 已经通过认证的

        // 调用formLogin()方法，表示启用登录表单页，不调用则不启用
        // 当未通过认证，却尝试访问需要认证的资源时：
        // -- 如果启用了登录表单页，则自动重定向到登录表单页
        // -- 如果未启用登录表单页，则响应403
        // http.formLogin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
