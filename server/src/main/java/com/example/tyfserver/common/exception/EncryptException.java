package com.example.tyfserver.common.exception;

public class EncryptException extends BaseException {
    public static final String ERROR_CODE = "common-003";;
    private static final String MESSAGE = "데이터를 암호화 하지 못했습니다.";

    public EncryptException() {
        super(ERROR_CODE, MESSAGE);
    }
}
