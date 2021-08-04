package com.example.tyfserver.auth.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;


@RedisHash("verificationCode")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VerificationCode {

    @Id
    private String id;

    private String value;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private Long timeout;

    public VerificationCode(String id, String value) {
        this.id = id; // todo: Prefix or Subfix
        this.value = value;
    }
}
