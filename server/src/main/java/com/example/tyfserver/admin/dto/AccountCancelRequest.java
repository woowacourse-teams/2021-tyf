package com.example.tyfserver.admin.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountCancelRequest {
    private String cancelReason;

    public AccountCancelRequest(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
