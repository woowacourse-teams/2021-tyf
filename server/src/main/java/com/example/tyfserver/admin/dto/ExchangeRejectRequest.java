package com.example.tyfserver.admin.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeRejectRequest {

    private String pageName;
    private String reason;

    public ExchangeRejectRequest(String pageName, String reason) {
        this.pageName = pageName;
        this.reason = reason;
    }
}
