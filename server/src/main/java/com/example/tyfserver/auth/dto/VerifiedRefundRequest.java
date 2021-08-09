package com.example.tyfserver.auth.dto;

import lombok.Getter;

@Getter
// todo 클래스명 정하기.
//  request body가 아닌 아규먼트리졸버를 통해 만들어지는 객체인데 Request라고 해도 될까?
public class VerifiedRefundRequest {

    private String merchantUid;

    public VerifiedRefundRequest(String merchantUid) {
        this.merchantUid = merchantUid;
    }
}
