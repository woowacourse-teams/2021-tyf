package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class MemberNotFoundException extends BaseException {

    private static final String ERROR_CODE = "member-001";
    private static final String MESSAGE = "해당 회원을 찾을 수 없습니다.";

    public MemberNotFoundException() {
        super(ERROR_CODE, MESSAGE);
    }
}
