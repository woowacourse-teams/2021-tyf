package com.example.tyfserver.auth.repository;

import com.example.tyfserver.auth.domain.VerificationCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataRedisTest
class VerificationCodeRepositoryTest {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Test
    void save() {
        //given
        VerificationCode verificationCode = new VerificationCode(null, "value");

        //when
        VerificationCode savedVerificationCode = verificationCodeRepository.save(verificationCode);

        //then
        assertThat(savedVerificationCode.getValue()).isEqualTo(verificationCode.getValue());
        assertThat(savedVerificationCode.getId()).isEqualTo(verificationCode.getId());
        System.out.println("savedVerificationCode = " + savedVerificationCode.getId());
    }

    @Test
    void get() {
        //given

        //when

        //then
    }
}
