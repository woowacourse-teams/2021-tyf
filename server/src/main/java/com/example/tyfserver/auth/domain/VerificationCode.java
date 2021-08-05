package com.example.tyfserver.auth.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


@RedisHash("verificationCode")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VerificationCode {

    private static final int CODE_LENGTH = 6;
    private static final int DEFAULT_TTL = 5;

    @Id
    private String merchantUid; // todo id or merchantUid?

    private String code;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private Integer timeout = DEFAULT_TTL;

    public VerificationCode(String merchantUid, String code) {
        this.merchantUid = merchantUid;
        this.code = code;
    }

    public static VerificationCode newCode(String merchantUid) {
        return new VerificationCode(merchantUid, createNewCode());
    }

    private static String createNewCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(RandomUtils.nextInt(0, 9));
        }
        return sb.toString();
    }

    public void verify(String code) {
        if (!Objects.equals(this.code, code)) {
            // todo 예외 정하기
            throw new RuntimeException();
        }
    }
}
