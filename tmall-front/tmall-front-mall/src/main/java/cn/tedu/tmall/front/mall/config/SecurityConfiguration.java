package cn.tedu.tmall.front.mall.config;

import cn.tedu.tmall.common.enumerator.ServiceCode;
import cn.tedu.tmall.common.web.JsonResult;
import cn.tedu.tmall.front.mall.filter.JwtAuthorizationFilter;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

/**
 * Spring Security的配置类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Slf4j
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfiguration() {
        log.debug("创建配置类对象：SecurityConfiguration");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置Security框架不使用Session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 将自定义的解析JWT的过滤器添加到Security框架的过滤器链中
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        // 允许跨域访问
        http.cors();

        // 处理“无认证信息却访问需要认证的资源时”的响应
        http.exceptionHandling().authenticationEntryPoint((request, response, e) -> {
            log.warn("Spring Security捕获到异常，由AuthenticationEntryPoint进行处理：", e);
            response.setContentType("application/json; charset=utf-8");
            String message = "操作失败，您当前未登录！";
            JsonResult jsonResult = JsonResult.fail(ServiceCode.ERROR_UNAUTHORIZED, message);
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(jsonResult));
            writer.close();
        });

        // 白名单
        String[] urls = {
                "/favicon.ico",
                "/doc.html",
                "/**/*.css",
                "/**/*.js",
                "/swagger-resources",
                "/v2/api-docs",
                "/categories/**", // 类别的所有
                "/goods/**", // 商品的所有
                "/comments/list-by-goods", // 评论列表
                "/pay/**" //支付相关
        };

        // 禁用“防止伪造的跨域攻击的防御机制”
        http.csrf().disable();

        // 配置请求授权
        http.authorizeRequests()
                .mvcMatchers(urls) // 匹配某些请求
                .permitAll() // 许可，即不需要通过认证就可以访问
                .anyRequest() // 任何请求，从执行效果来看，也可以视为：除了以上配置过的以外的其它请求
                .authenticated(); // 需要通过认证才可以访问
    }

}
