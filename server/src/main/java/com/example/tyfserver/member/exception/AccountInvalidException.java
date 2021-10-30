package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class AccountInvalidException extends BaseException {

    public static final String ERROR_CODE = "member-010";
    private static final String MESSAGE = "유효하지 않은 계좌입니다.";

    public AccountInvalidException() {
        super(ERROR_CODE, MESSAGE);
    }
}
