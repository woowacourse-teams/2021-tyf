package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class AccountRequestingException extends BaseException {
    public static final String ERROR_CODE = "member-008";
    private static final String MESSAGE = "계좌등록 절차가 진행중입니다.";

    public AccountRequestingException() {
        super(ERROR_CODE, MESSAGE);
    }
}
