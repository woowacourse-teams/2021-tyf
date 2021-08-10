package com.example.tyfserver.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountCancelRequest {
    private String cancelReason;

    public AccountCancelRequest(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
