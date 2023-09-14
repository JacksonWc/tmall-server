package cn.tedu.tmall.basic.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.tedu.tmall.basic.dao.persist.mapper")
public class MyBatisConfiguration {
}
