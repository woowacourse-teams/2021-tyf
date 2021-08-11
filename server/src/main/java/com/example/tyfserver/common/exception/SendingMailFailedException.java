package com.example.tyfserver.common.exception;

public class SendingMailFailedException extends BaseException {
    public static final String ERROR_CODE = "common-001";
    private static final String MESSAGE = "메일을 보내지 못하였습니다.";

    public SendingMailFailedException() {
        super(ERROR_CODE, MESSAGE);
    }
}
