package cn.tedu.tmall.common.authentication;

import lombok.Data;

import java.io.Serializable;

/**
 * 当前认证信息中的当事人
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
public class CurrentPrincipal implements Serializable {

    /**
     * 当事人ID
     */
    private Long id;

    /**
     * 当事人用户名
     */
    private String username;

}
