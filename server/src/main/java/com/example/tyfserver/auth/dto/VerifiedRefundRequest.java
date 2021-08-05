package com.example.tyfserver.auth.dto;

import lombok.Getter;

@Getter
public class VerifiedRefundRequest {

    private String merchantUid;

    public VerifiedRefundRequest(String merchantUid) {
        this.merchantUid = merchantUid;
    }
}
