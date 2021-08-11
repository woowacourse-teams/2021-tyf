package com.example.tyfserver.admin.exception;

import com.example.tyfserver.common.exception.BaseException;

public class InvalidAdminException extends BaseException {

    public static final String ERROR_CODE = "admin-001";
    private static final String MESSAGE = "아이디 혹은 비밀번호가 일치하지 않습니다.";

    public InvalidAdminException() {
        super(ERROR_CODE, MESSAGE);
    }
}