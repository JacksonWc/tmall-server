package cn.tedu.tmall.admin.account.dao.persist;

import cn.tedu.tmall.admin.account.dao.persist.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTests {
    @Autowired
    UserMapper userMapper;

    @Test
    void ListManager(){
        System.out.println(userMapper.ListManager());
    }

}
