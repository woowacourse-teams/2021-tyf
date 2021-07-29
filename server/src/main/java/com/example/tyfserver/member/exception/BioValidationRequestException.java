package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class BioValidationRequestException extends BaseException {

    public static final String ERROR_CODE = "member-006";
    private static final String MESSAGE = "Bio 형식이 올바르지 않습니다.";

    public BioValidationRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
