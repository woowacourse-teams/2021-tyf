package com.thankyou_for.auth.exception;

import com.thankyou_for.auth.dto.VerificationFailedErrorResponse;
import com.thankyou_for.common.dto.ErrorResponse;
import com.thankyou_for.common.exception.BaseException;

public class VerificationFailedException extends BaseException {

    public static final String ERROR_CODE = "refund-001";
    private static final String MESSAGE = "승인번호가 일치하지 않습니다.";

    private final int remainTryCount;

    public VerificationFailedException(int remainTryCount) {
        super(ERROR_CODE, MESSAGE);
        this.remainTryCount = remainTryCount;
    }

    @Override
    public ErrorResponse toResponse() {
        return new VerificationFailedErrorResponse(getErrorCode(), getMessage(), remainTryCount);
    }
}
