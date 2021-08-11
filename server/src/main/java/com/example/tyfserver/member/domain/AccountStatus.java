package com.example.tyfserver.member.domain;

import lombok.Getter;

@Getter
public enum AccountStatus {
    UNREGISTERED("미등록"), REGISTERED("등록완료"), REQUESTING("등록요청"), REJECTED("등록반려");

    private final String information;

    AccountStatus(String info) {
        this.information = info;
    }
}
