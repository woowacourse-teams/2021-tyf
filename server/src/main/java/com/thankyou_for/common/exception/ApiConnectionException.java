package com.thankyou_for.common.exception;

public class ApiConnectionException extends BaseException {

    private static final String ERROR_CODE = "common-004";
    private static final String MESSAGE = "EXTERNAL API TIME OUT";

    public ApiConnectionException() {
        super(ERROR_CODE, MESSAGE);
    }
}
