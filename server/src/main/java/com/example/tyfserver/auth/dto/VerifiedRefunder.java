package com.example.tyfserver.auth.dto;

import lombok.Getter;

@Getter
public class VerifiedRefunder {

    private String merchantUid;

    public VerifiedRefunder(String merchantUid) {
        this.merchantUid = merchantUid;
    }
}
