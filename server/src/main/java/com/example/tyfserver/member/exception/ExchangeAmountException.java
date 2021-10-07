package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class ExchangeAmountException extends BaseException {

    public static final String ERROR_CODE = "exchange-002";
    private static final String MESSAGE = "만원 이하의 금액은 정산신청을 할 수가 없습니다.";

    public ExchangeAmountException() {
        super(ERROR_CODE, MESSAGE);
    }
}
