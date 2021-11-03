package com.thankyou_for.member.domain;

import lombok.Getter;

@Getter
public enum AccountStatus {
    // todo 어드민 정산할 때 REGISTERED는 확인을 안하네? 정산신청할 때 확인하니 괜찮나?
    UNREGISTERED("미등록"), REGISTERED("등록완료"), REQUESTING("등록요청"), REJECTED("등록반려");

    private final String information;

    AccountStatus(String info) {
        this.information = info;
    }
}
