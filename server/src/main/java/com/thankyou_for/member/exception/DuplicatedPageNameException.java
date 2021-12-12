package com.thankyou_for.member.exception;

import com.thankyou_for.common.exception.BaseException;

public class DuplicatedPageNameException extends BaseException {

    public static final String ERROR_CODE = "member-003";
    private static final String MESSAGE = "이미 존재하는 pageName 입니다.";

    public DuplicatedPageNameException() {
        super(ERROR_CODE, MESSAGE);
    }
}
