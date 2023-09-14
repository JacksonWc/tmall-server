package cn.tedu.tmall.admin.account.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.tedu.tmall.admin.account.dao.persist.mapper")
public class MybatisConfig {
}
