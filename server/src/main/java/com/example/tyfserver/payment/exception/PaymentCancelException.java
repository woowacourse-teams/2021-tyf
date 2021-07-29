package com.example.tyfserver.payment.exception;

import com.example.tyfserver.common.exception.BaseException;

public class PaymentCancelException extends BaseException {

    public static final String ERROR_CODE = "payment-005";
    private static final String MESSAGE = "아임포트에서 환불이 완료되지 않았습니다.";

    public PaymentCancelException() {
        super(ERROR_CODE, MESSAGE);
    }
}
