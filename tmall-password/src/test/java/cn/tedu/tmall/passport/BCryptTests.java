package cn.tedu.tmall.passport;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptTests {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);

    @Test
    void encode() {
        String s = "123456";
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            // String encodedPassword =
                    passwordEncoder.encode(s);
            // System.out.println(encodedPassword);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

//    $2a$10$u1H/zFV2WDnIyN1XZGk2zuMYJthSZggKAmc6VFk2PFnqoYmv5E.ry
//    $2a$10$irm/SCD0K8w8.gZ42Wiiq.zPl44hBfh4kHzi04pP5HIGPSbp.yAyG
//    $2a$10$iSWV5KAJng9LQlo64SYQjuGs46jtbR71i/hZ6JuH8XKXk8XIcQCuG
//    $2a$10$njKUciXxnDjJ96A9Cyu5PuePdm5H8hfrAKXK0cRS8TU4R2dpLkqO2
//    $2a$10$K6tOAAsNqulVGy.0BXHIGOaOco15xr5u0pNSY4oWpGT2jfcS22nTa

    @Test
    void matches() {
        String s = "123456";
        String encodedPassword = "$2a$10$iSWV5KAJng9LQlo64SYQjuGs46jtbR71i/hZ6JuH8XKXk8XIcQCuG";
        boolean matches = passwordEncoder.matches(s, encodedPassword);
        System.out.println(matches);
    }

}
