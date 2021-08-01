package com.example.tyfserver.payment.exception;

import com.example.tyfserver.common.exception.BaseException;

public class PaymentCancelRequestException extends BaseException {

    public static final String ERROR_CODE = "payment-008";
    private static final String MESSAGE = "환불요청 시에 넘기는 Request의 값이 유효한 값이 아닙니다.";

    public PaymentCancelRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
