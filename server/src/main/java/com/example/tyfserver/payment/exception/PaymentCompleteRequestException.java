package com.example.tyfserver.payment.exception;

import com.example.tyfserver.common.exception.BaseException;

public class PaymentCompleteRequestException extends BaseException {

    public static final String ERROR_CODE = "payment-015";
    private static final String MESSAGE = "Payment 확정 시에 넘기는 Request의 값이 유효한 값이 아닙니다.";

    public PaymentCompleteRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
