package com.thankyou_for.member.exception;

import com.thankyou_for.common.exception.BaseException;

public class AccountRequestingException extends BaseException {
    public static final String ERROR_CODE = "member-008";
    private static final String MESSAGE = "계좌등록 절차가 진행중입니다.";

    public AccountRequestingException() {
        super(ERROR_CODE, MESSAGE);
    }
}
