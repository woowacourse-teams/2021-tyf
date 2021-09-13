package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class NotEnoughPointException extends BaseException {

    public static final String ERROR_CODE = "donation-010";
    private static final String MESSAGE = "보유 포인트가 충분하지 않습니다.";

    public NotEnoughPointException() {
        super(ERROR_CODE, MESSAGE);
    }
}
