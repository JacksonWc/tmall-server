package cn.tedu.tmall.passport.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.tedu.tmall.passport.dao.persist.mapper")
public class MyBatisConfiguration {
}
