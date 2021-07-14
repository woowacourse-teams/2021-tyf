package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class DuplicatedNicknameException extends BaseException {

    private static final String ERROR_CODE = "member-005";
    private static final String MESSAGE = "이미 존재하는 nickname 입니다.";

    public DuplicatedNicknameException() {
        super(ERROR_CODE, MESSAGE);
    }
}
