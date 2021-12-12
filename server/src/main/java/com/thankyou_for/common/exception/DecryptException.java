package com.thankyou_for.common.exception;

public class DecryptException extends BaseException {

    public static final String ERROR_CODE = "common-002";
    private static final String MESSAGE = "데이터를 복호화 하지 못했습니다.";

    public DecryptException() {
        super(ERROR_CODE, MESSAGE);
    }
}
