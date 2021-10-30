package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class AccountHolderNameInvalidException extends BaseException {

    public static final String ERROR_CODE = "member-011";
    private static final String MESSAGE = "계좌주 이름과 입력하신 이름이 일치하지 않습니다.";

    public AccountHolderNameInvalidException() {
        super(ERROR_CODE, MESSAGE);
    }
}
