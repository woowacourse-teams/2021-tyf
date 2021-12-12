package com.thankyou_for.payment.exception;

import com.thankyou_for.common.exception.BaseException;

public class PaymentPendingRequestException extends BaseException {

    public static final String ERROR_CODE = "payment-001";
    private static final String MESSAGE = "결제정보 생성 시에 넘기는 Request의 값이 유효한 값이 아닙니다.";

    public PaymentPendingRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
