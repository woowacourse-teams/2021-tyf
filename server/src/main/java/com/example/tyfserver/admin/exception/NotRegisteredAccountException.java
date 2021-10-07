package com.example.tyfserver.admin.exception;

import com.example.tyfserver.common.exception.BaseException;

public class NotRegisteredAccountException extends BaseException {

    public static final String ERROR_CODE = "admin-003";
    private static final String MESSAGE = "정산계좌가 승인되지 않은 사용자기 때문에 정산을 신청할 수 없습니다.";

    public NotRegisteredAccountException() {
        super(ERROR_CODE, MESSAGE);
    }
}