package cn.tedu.tmall.front.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.tedu.tmall.front.mall.dao.persist.mapper")
public class MybatisConfig {
}
