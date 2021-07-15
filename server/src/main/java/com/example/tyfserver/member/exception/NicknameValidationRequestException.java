package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class NicknameValidationRequestException extends BaseException {

    public static final String ERROR_CODE = "member-004";
    private static final String MESSAGE = "Nickname 유효 검사 시에 넘기는 Request의 값이 유효한 값이 아닙니다.";

    public NicknameValidationRequestException() {
        super(ERROR_CODE, MESSAGE);
    }
}
