package com.thankyou_for.member.exception;

import com.thankyou_for.common.exception.BaseException;

public class MemberNotFoundException extends BaseException {

    public static final String ERROR_CODE = "member-001";
    private static final String MESSAGE = "해당 회원을 찾을 수 없습니다.";

    public MemberNotFoundException() {
        super(ERROR_CODE, MESSAGE);
    }
}
