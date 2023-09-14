package cn.tedu.tmall.admin.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.tedu.tmall.admin.mall.dao.persist.mapper")
public class MybatisConfig {
}
