package com.example.tyfserver.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Aes256UtilTest {

    @Autowired
    Aes256Util aes256Util;

    @DisplayName("암호화 된 데이터를 복호화 한다.")
    @Test
    void decrypt() {
        String aes = aes256Util.encrypt("1234-1234-1234-1234");
        assertThat(aes256Util.decrypt(aes)).isEqualTo("1234-1234-1234-1234");
    }
}
