package cn.tedu.tmall.passport.cache;


import cn.tedu.tmall.passport.dao.cache.IUserCacheRepository;
import cn.tedu.tmall.common.po.UserStatePO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserCacheRepositoryTests {

    @Autowired
    IUserCacheRepository repository;

    @Test
    void saveUserState() {
        Long id = 998L;

        UserStatePO userStatePO = new UserStatePO();
        userStatePO.setEnable(1);
        userStatePO.setAuthoritiesJsonString("test2");

        repository.saveUserState(id, userStatePO);
    }

    @Test
    void getUserState() {
        Long id = 998L;
        UserStatePO userState = repository.getUserState(id);
        System.out.println(userState);
    }

}
