package com.example.tyfserver.common.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Aes256UtilTest {

    @Autowired
    Aes256Util aes256Util;

    @Test
    void encrypt() {
        System.out.println("result123 : " + aes256Util.encrypt("1234-1234-1234"));
    }

    @Test
    void decrypt() {
        String aes = aes256Util.encrypt("");
        System.out.println("result123:" + aes256Util.decrypt(aes));

    }
}