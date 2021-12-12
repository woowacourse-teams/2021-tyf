package com.thankyou_for.member.exception;

import com.thankyou_for.common.exception.BaseException;

public class BioValidationRequestException extends BaseException {

    public static final String ERROR_CODE = "member-006";
    private static final String MESSAGE = "Bio 형식이 올바르지 않습니다.";

    public BioValidationRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
