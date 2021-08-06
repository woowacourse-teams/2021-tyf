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


@RedisHash("codeResendCoolTime")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CodeResendCoolTime {

    private static final int DEFAULT_TTL = 1 * 60;

    @Id
    private String merchantUid;

    @TimeToLive
    private Integer timeout = DEFAULT_TTL;

    public CodeResendCoolTime(String merchantUid) {
        this.merchantUid = merchantUid;
    }
}
