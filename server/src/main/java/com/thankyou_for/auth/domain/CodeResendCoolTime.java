package com.thankyou_for.auth.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


@RedisHash("codeResendCoolTime")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CodeResendCoolTime {

    public static final int DEFAULT_TTL = 1 * 60;

    @Id
    private String merchantUid;

    @TimeToLive
    private Integer timeout = DEFAULT_TTL;

    public CodeResendCoolTime(String merchantUid, Integer timeout) {
        this.merchantUid = merchantUid;
        this.timeout = timeout;
    }

    public CodeResendCoolTime(String merchantUid) {
        this(merchantUid, DEFAULT_TTL);
    }
}
