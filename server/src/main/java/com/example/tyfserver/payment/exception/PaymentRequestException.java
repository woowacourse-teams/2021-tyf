package com.example.tyfserver.payment.exception;

import com.example.tyfserver.common.exception.BaseException;

public class PaymentRequestException extends BaseException {

    public static final String ERROR_CODE = "payment-001";
    private static final String MESSAGE = "결제 요청 데이터 유효하지 않음";

    public PaymentRequestException() {
        super(ERROR_CODE, MESSAGE);
    }

}
