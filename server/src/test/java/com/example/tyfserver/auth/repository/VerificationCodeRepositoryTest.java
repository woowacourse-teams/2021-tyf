package com.example.tyfserver.auth.repository;

import com.example.tyfserver.auth.domain.VerificationCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataRedisTest
class VerificationCodeRepositoryTest {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Test
    void save() {
        //given
        String merchantUid = "merchantUid";
        String code = "code";
        VerificationCode verificationCode = new VerificationCode(merchantUid, code);

        //when
        verificationCodeRepository.save(verificationCode);
        VerificationCode findVerificationCode = verificationCodeRepository.findById(merchantUid).get();

        //then
        assertThat(findVerificationCode.getCode()).isEqualTo(code);
        assertThat(findVerificationCode.getMerchantUid()).isEqualTo(merchantUid);
    }
}
