package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class NotRefundableMember extends BaseException {

    public static final String ERROR_CODE = "member-009";
    private static final String MESSAGE = "정산 계좌를 등록하지 않은 창작자입니다.";

    public NotRefundableMember() {
        super(ERROR_CODE, MESSAGE);
    }
}
