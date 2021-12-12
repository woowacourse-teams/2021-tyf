package com.thankyou_for.admin.exception;

import com.thankyou_for.common.exception.BaseException;

public class ExchangeDoesNotAppliedException extends BaseException {

    public static final String ERROR_CODE = "admin-002";
    private static final String MESSAGE = "사용자가 정산을 신청하지 않았습니다.";

    public ExchangeDoesNotAppliedException() {
        super(ERROR_CODE, MESSAGE);
    }
}