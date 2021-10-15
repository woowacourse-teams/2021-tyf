package com.example.tyfserver.member.exception;

import com.example.tyfserver.common.exception.BaseException;

public class WrongDonationOwnerException extends BaseException {

    public static final String ERROR_CODE = "donation-005";
    public static final String MESSAGE = "도네이션의 주인이 아닌 사용자입니다.";

    public WrongDonationOwnerException() {
        super(ERROR_CODE, MESSAGE);
    }
}
