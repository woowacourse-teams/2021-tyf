package com.example.tyfserver.payment.exception;

import com.example.tyfserver.common.exception.BaseException;

public class PaymentRequestException extends BaseException {

    public static final String ERROR_CODE_NOT_PAID = "payment-002";
    public static final String ERROR_CODE_INVALID_MERCHANT_ID = "payment-003";
    public static final String ERROR_CODE_INVALID_AMOUNT = "payment-004";
    public static final String ERROR_INVALID_CREATOR = "payment-005";
    private static final String MESSAGE = "결제 요청 데이터 유효하지 않음";

    public PaymentRequestException(String code) {
        super(code, MESSAGE);
    }

}
