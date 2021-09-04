package com.example.tyfserver.payment.exception;

import com.example.tyfserver.common.exception.BaseException;

public class ItemNotFoundException extends BaseException {

    public static final String ERROR_CODE = "payment-014";
    private static final String MESSAGE = "존재하지 않는 ItemId 입니다.";

    public ItemNotFoundException() {
        super(ERROR_CODE, MESSAGE);
    }
}
