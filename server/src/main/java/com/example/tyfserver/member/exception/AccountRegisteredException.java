package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class AccountRegisteredException extends BaseException {
    public static final String ERROR_CODE = "member-007";
    private static final String MESSAGE = "이미 계좌가 등록되어 있습니다.";

    public AccountRegisteredException() {
        super(ERROR_CODE, MESSAGE);
    }
}
