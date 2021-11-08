package com.thankyou_for.member.exception;

import com.thankyou_for.common.exception.BaseException;

public class NicknameValidationRequestException extends BaseException {

    public static final String ERROR_CODE = "member-004";
    private static final String MESSAGE = "Nickname 형식이 올바르지 않습니다.";

    public NicknameValidationRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
