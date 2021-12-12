package com.thankyou_for.admin.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountRejectRequest {
    private String rejectReason;

    public AccountRejectRequest(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
