package cn.tedu.tmall.passport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTests {

    String secretKey = "8KS7u4F9ufaJa7sPD3fDa";

    @Test
    void generate() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 9527);
        claims.put("username", "zhangsan");

        Date date = new Date(System.currentTimeMillis() + 10l*365*24*60* 60 * 1000);

        String jwt = Jwts.builder()
                // Header
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                // Payload
                .setClaims(claims)
                .setExpiration(date)
                // Verify Signature
                .signWith(SignatureAlgorithm.HS256, secretKey)
                // 完成
                .compact();
        System.out.println(jwt);
    }

    @Test
    void parse() {
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OTUyNywiZXhwIjoxNjkzODg0MTY2LCJ1c2VybmFtZSI6InpoYW5nc2FuIn0.FCTN3_0WozhP5HAPU-ITGblAWbLuXWFngVKMdrdT-VA";

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();

        Long id = claims.get("id",Long.class);
        String username = claims.get("username",String.class);

        System.out.println("id = " + id);
        System.out.println("username = " + username);
    }

}
