package com.example.tyfserver.auth.util;

import com.example.tyfserver.auth.dto.IdAndEmail;
import com.example.tyfserver.auth.exception.InvalidTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("이메일 값이 유효하지 않은 토큰 유효성 검증")
    void testTokenWithNotValidEmailCase() {
        //when //then
        assertThatThrownBy(() -> jwtTokenProvider.validateToken("invalidToken"))
                .isExactlyInstanceOf(InvalidTokenException.class);
    }

    @Test
    @DisplayName("만료시간이 지난 토큰에 대한 유효성 검증")
    void testTokenWithNotValidExpirationCase() {
        Date date = new Date(0);

        String token = Jwts.builder()
                .claim("email", "token")
                .setExpiration(new Date(date.getTime() + 1))
                .signWith(SignatureAlgorithm.HS256, "secreteKey")
                .compact();

        //when //then
        assertThatThrownBy(() -> jwtTokenProvider.validateToken(token))
                .isExactlyInstanceOf(InvalidTokenException.class);
    }

    @Test
    @DisplayName("토큰에서 이메일 추출")
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
