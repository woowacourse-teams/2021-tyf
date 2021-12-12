package com.thankyou_for.member.exception;

import com.thankyou_for.common.exception.BaseException;

public class AlreadyRequestExchangeException extends BaseException {

    public static final String ERROR_CODE = "exchange-001";
    private static final String MESSAGE = "이미 정산신청을 하셨습니다.";

    public AlreadyRequestExchangeException() {
        super(ERROR_CODE, MESSAGE);
    }
}
