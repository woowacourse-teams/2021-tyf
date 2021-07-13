package com.example.tyfserver.auth.domain;

import com.example.tyfserver.auth.dto.IdAndEmail;
import com.example.tyfserver.auth.util.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("이메일 값이 유효하지 않은 토큰 유효성 검증")
    @Test
    void testTokenWithNotValidEmailCase() {
        //when //then
        assertThatThrownBy(() -> jwtTokenProvider.validateToken("invalidToken"))
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("만료시간이 지난 토큰에 대한 유효성 검증")
    @Test
    void testTokenWithNotValidExpirationCase() {
        Date date = new Date(0);

        String token = Jwts.builder()
                .claim("email", "token")
                .setExpiration(new Date(date.getTime() + 1))
                .signWith(SignatureAlgorithm.HS256, "secreteKey")
                .compact();

        //when //then
        assertThatThrownBy(() -> jwtTokenProvider.validateToken(token))
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("토큰에서 이메일 추출")
    @Test
    void testFindEmailByToken() {
        //given
        long id = 1L;
        String expectedEmail = "abc@chocolate.com";
        String token = jwtTokenProvider.createToken(id, expectedEmail);
        //when
        IdAndEmail idAndEmail = jwtTokenProvider.findIdAndEmailFromToken(token);
        Long actualId = idAndEmail.getId();
        String actualEmail = idAndEmail.getEmail();
        //then
        assertThat(actualEmail).isEqualTo(expectedEmail);
        assertThat(actualId).isEqualTo(id);
    }
}
