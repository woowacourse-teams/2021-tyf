package com.thankyou_for.member.exception;

import com.thankyou_for.common.exception.BaseException;

public class NotEnoughPointException extends BaseException {

    public static final String ERROR_CODE = "donation-010";
    private static final String MESSAGE = "보유 포인트가 충분하지 않습니다.";

    public NotEnoughPointException() {
        super(ERROR_CODE, MESSAGE);
    }
}
