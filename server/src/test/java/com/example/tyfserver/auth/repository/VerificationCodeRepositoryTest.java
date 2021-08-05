package com.example.tyfserver.auth.repository;

import com.example.tyfserver.auth.domain.VerificationCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;

@DataRedisTest
class VerificationCodeRepositoryTest {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Test
    void save() {
        //given
        VerificationCode verificationCode1 = new VerificationCode(null, "value1");
        VerificationCode verificationCode2 = new VerificationCode(null, "value2");

        //when
        VerificationCode save = verificationCodeRepository.save(verificationCode1);
        verificationCodeRepository.save(verificationCode2);

        VerificationCode verificationCode = verificationCodeRepository.findById(save.getMerchantUid())
                .orElseThrow(RuntimeException::new);

        //then
//        assertThat(savedVerificationCode.getValue()).isEqualTo(verificationCode2.getValue());
//        assertThat(savedVerificationCode.getId()).isEqualTo(verificationCode2.getId());
//        System.out.println("savedVerificationCode = " + savedVerificationCode.getId());
    }
}
