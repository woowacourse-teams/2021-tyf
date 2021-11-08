package com.thankyou_for.member.exception;

import com.thankyou_for.common.exception.BaseException;

public class AccountInvalidException extends BaseException {

    public static final String ERROR_CODE = "member-010";
    private static final String MESSAGE = "유효하지 않은 계좌입니다.";

    public AccountInvalidException() {
        super(ERROR_CODE, MESSAGE);
    }
}
