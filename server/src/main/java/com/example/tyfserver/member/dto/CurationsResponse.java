package com.example.tyfserver.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurationsResponse {

    private String nickname;
    private Long donationAmount;

    public CurationsResponse(String nickname, Long donationAmount) {
        this.nickname = nickname;
        this.donationAmount = donationAmount;
    }
}
