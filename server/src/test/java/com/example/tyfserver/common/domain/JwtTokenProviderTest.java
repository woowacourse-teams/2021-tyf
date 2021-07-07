package com.example.tyfserver.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("유효하지 않은 토큰에 대한 토큰 유효성 검증")
    @Test
    void testTokenWithNotValidCase() {
        //given
        String token = jwtTokenProvider.createToken("abc@chocolate.com");
        //when //then
        assertThatThrownBy(() -> jwtTokenProvider.validateToken("invalidToken"))
            .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("토큰에서 이메일 추출")
    @Test
    void testFindEmailByToken() {
        //given
        String expectedEmail = "abc@chocolate.com";
        String token = jwtTokenProvider.createToken(expectedEmail);
        //when
        String actualEmail = jwtTokenProvider.findEmailByToken(token);
        //then
        assertThat(actualEmail).isEqualTo(expectedEmail);
    }
}
