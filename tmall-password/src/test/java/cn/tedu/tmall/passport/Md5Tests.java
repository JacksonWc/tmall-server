package cn.tedu.tmall.passport;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class Md5Tests {

    @Test
    void md5DigestAsHex() {
        String salt = UUID.randomUUID().toString();
        String s = "123456"; // e10adc3949ba59abbe56e057f20f883e
        String result = DigestUtils.md5DigestAsHex((s + salt).getBytes());
        System.out.println(result + salt);
        // 04541dff45d73694cd7fc870388adff50c5a8791-632b-4df8-8d41-cfcd653a1bf4
        // edbaaecea317b5fdb3b0d04503b1201dde6a5301-9f64-4eef-8c45-56c922a2ff8f
    }

    @Test
    void matches() {
        String s = "123456";
        String dbPassword = "edbaaecea317b5fdb3b0d04503b1201dde6a5301-9f64-4eef-8c45-56c922a2ff8f";
        String salt = dbPassword.substring(32);
        String encodedValue = DigestUtils.md5DigestAsHex((s + salt).getBytes());
        boolean matches = dbPassword.equals(encodedValue + salt);
        System.out.println(matches);
    }

}
