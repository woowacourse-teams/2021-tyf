package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class PageNameValidationRequestException extends BaseException {

    public static final String ERROR_CODE = "member-002";
    private static final String MESSAGE = "PageName 유효 검사 시에 넘기는 Request의 값이 유효한 값이 아닙니다.";

    public PageNameValidationRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
