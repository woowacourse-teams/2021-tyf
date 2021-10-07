package com.example.tyfserver.member.domain;

import lombok.Getter;

@Getter
public enum ExchangeStatus {
    WAITING("대기중"), APPROVED("승인됨"), REJECTED("반려됨");

    private final String information;

    ExchangeStatus(String info) {
        this.information = info;
    }
}
