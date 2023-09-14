package cn.tedu.tmall.passport;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UUIDTests {

    @Test
    void randomUUID() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 3000000; i++) {
            // System.out.println(UUID.randomUUID());
            UUID.randomUUID();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
