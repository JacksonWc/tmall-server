package cn.tedu.tmall.common.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserStatePO implements Serializable {

    /**
     * 账号的启用状态：1=启用，0=禁用
     */
    private Integer enable;

    /**
     * 账号的权限列表
     */
    private String authoritiesJsonString;

}
