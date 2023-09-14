package cn.tedu.tmall.passport.pojo.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginInfoParam implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码（密文）
     */
    private String password;

}
