package com.example.tyfserver.auth.repository;

import com.example.tyfserver.auth.domain.VerificationCode;
import com.example.tyfserver.common.config.EmbeddedRedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataRedisTest
@ActiveProfiles("test")
@Import(value = {EmbeddedRedisConfig.class})
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
