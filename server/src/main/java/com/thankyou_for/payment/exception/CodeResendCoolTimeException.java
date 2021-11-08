package com.thankyou_for.payment.exception;

import com.thankyou_for.common.exception.BaseException;

public class CodeResendCoolTimeException extends BaseException {

    public static final String ERROR_CODE = "payment-011";
    private static final String MESSAGE = "인증번호 재전송 대기시간입니다.";

    public CodeResendCoolTimeException() {
        super(ERROR_CODE, MESSAGE);
    }
}
