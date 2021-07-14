package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class DuplicatedPageNameException extends BaseException {

    private static final String ERROR_CODE = "member-003";
    private static final String MESSAGE = "이미 존재하는 pageName 입니다.";

    public DuplicatedPageNameException() {
        super(ERROR_CODE, MESSAGE);
    }
}
