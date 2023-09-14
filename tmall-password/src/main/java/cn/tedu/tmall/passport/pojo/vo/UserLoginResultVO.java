package cn.tedu.tmall.passport.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserLoginResultVO implements Serializable {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * Token（JWT）
     */
    private String token;

    /**
     * 权限列表
     */
    private List<String> authorities;

}
