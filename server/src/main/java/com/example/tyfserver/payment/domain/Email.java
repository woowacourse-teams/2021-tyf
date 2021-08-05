package com.example.tyfserver.payment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    // todo 멤버 도메인의 이메일도 얘로 바꿔줘야 하나???
    private String email;

    public Email(String email) {
        this.email = email;
    }

    public String maskedEmail() {
        int at = email.indexOf("@");
        int maskLen = at / 2;
        int start = (at - maskLen) / 2;

        return email.substring(0, start) + "*".repeat(maskLen) + email.substring(start + maskLen);
    }
}
