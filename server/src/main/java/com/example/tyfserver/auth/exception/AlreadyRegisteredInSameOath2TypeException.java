package com.example.tyfserver.auth.exception;

import com.example.tyfserver.common.dto.ErrorResponse;
import com.example.tyfserver.common.dto.ErrorWithTokenResponse;
import com.example.tyfserver.common.exception.BaseException;

public class AlreadyRegisteredInSameOath2TypeException extends BaseException {

    private static final String ERROR_CODE = "auth-004";
    private static final String MESSAGE = "이미 같은 OAuth2 타입으로 가입되어 있는 사용자입니다. 토큰첨부.";

    private final String token;

    public AlreadyRegisteredInSameOath2TypeException(String token) {
        super(ERROR_CODE, MESSAGE);
        this.token = token;
    }

    @Override
    public ErrorResponse toResponse() {
        return new ErrorWithTokenResponse(getErrorCode(), getMessage(), token);
    }
}
