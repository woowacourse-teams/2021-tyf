package com.thankyou_for.member.exception;

import com.thankyou_for.common.exception.BaseException;

public class AccountAlreadyRegisteredException extends BaseException {
    public static final String ERROR_CODE = "member-007";
    private static final String MESSAGE = "이미 계좌가 등록되어 있습니다.";

    public AccountAlreadyRegisteredException() {
        super(ERROR_CODE, MESSAGE);
    }
}
