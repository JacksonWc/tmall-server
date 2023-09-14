package cn.tedu.tmall.admin.content.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.tedu.tmall.admin.content.dao.persist.mapper")
public class MybatisConfig {
}
