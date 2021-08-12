package com.example.tyfserver.payment.exception;

import com.example.tyfserver.common.exception.BaseException;

public class RefundVerificationBlockedException extends BaseException {

    public static final String ERROR_CODE = "payment-012";
    private static final String MESSAGE = "해당 주문번호의 인증시도 횟수를 소진했습니다. 고객센터로 문의가 필요합니다.";

    public RefundVerificationBlockedException() {
        super(ERROR_CODE, MESSAGE);
    }
}
