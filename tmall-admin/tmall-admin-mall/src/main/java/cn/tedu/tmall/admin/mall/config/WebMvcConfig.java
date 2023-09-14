package cn.tedu.tmall.admin.mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//配置跨域访问
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //服务器的哪些路径可以访问
                .allowedOriginPatterns("*") //客户端来自哪里
                .allowedHeaders("*")//请求头的配置要求
                .allowedMethods("*")//请求类型
                .allowCredentials(true)//协议凭证，一般指session id
                .maxAge(3600);//多久之内不再检查跨域访问
    }
}
