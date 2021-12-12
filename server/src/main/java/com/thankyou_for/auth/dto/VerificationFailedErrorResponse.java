package com.thankyou_for.auth.dto;

import com.thankyou_for.common.dto.ErrorResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerificationFailedErrorResponse extends ErrorResponse {

    private int remainTryCount;

    public VerificationFailedErrorResponse(String errorCode, String message, int remainTryCount) {
        super(errorCode, message);
        this.remainTryCount = remainTryCount;
    }
}

