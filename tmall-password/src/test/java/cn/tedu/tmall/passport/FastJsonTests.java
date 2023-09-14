package cn.tedu.tmall.passport;

import cn.tedu.tmall.passport.pojo.vo.UserLoginInfoVO;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FastJsonTests {

//    对象转换为json
    @Test
    void toJSONString() {
        UserLoginInfoVO loginInfo = new UserLoginInfoVO();
        loginInfo.setId(1L);
        loginInfo.setUsername("root");
        loginInfo.setPassword("123456");
        loginInfo.setEnable(1);

        String jsonString = JSON.toJSONString(loginInfo);
        System.out.println(jsonString);
    }

//    对象的list转换为json
    @Test
    void toJSONString2() {
        List<UserLoginInfoVO> loginInfoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserLoginInfoVO loginInfo = new UserLoginInfoVO();
            loginInfo.setId(0L + i);
            loginInfo.setUsername("root" + i);
            loginInfo.setPassword("123456_" + i);
            loginInfo.setEnable(1);
            loginInfoList.add(loginInfo);
        }

        String jsonString = JSON.toJSONString(loginInfoList);
        System.out.println(jsonString);
    }

//    json转换为对象
    @Test
    void parseObject() {
        String jsonString = "{\"enable\":1,\"id\":1,\"password\":\"123456\",\"username\":\"root\"}";
        UserLoginInfoVO loginInfo = JSON.parseObject(jsonString, UserLoginInfoVO.class);
        System.out.println(loginInfo);
    }

//    json转换为对象的list
    @Test
    void parseArray() {
        String jsonString = "[{\"enable\":1,\"id\":0,\"password\":\"123456_0\",\"username\":\"root0\"},{\"enable\":1,\"id\":1,\"password\":\"123456_1\",\"username\":\"root1\"},{\"enable\":1,\"id\":2,\"password\":\"123456_2\",\"username\":\"root2\"},{\"enable\":1,\"id\":3,\"password\":\"123456_3\",\"username\":\"root3\"},{\"enable\":1,\"id\":4,\"password\":\"123456_4\",\"username\":\"root4\"}]";
        List<UserLoginInfoVO> loginInfoList = JSON.parseArray(jsonString, UserLoginInfoVO.class);
        System.out.println("集合长度：" + loginInfoList.size());
        for (UserLoginInfoVO loginInfo : loginInfoList) {
            System.out.println(loginInfo);
        }
    }

}
