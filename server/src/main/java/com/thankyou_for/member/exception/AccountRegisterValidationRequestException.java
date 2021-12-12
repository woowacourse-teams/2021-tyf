package com.thankyou_for.member.exception;

import com.thankyou_for.common.exception.BaseException;

public class AccountRegisterValidationRequestException extends BaseException {
    public static final String ERROR_CODE = "member-009";
    private static final String MESSAGE = "요청된 계좌의 정보의 형식이 올바르지 않습니다.";

    public AccountRegisterValidationRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
