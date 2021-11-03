package com.thankyou_for.common.exception;

import com.thankyou_for.common.dto.ErrorResponse;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final String errorCode;

    public BaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorResponse toResponse() {
        return new ErrorResponse(getErrorCode(), getMessage());
    }
}
