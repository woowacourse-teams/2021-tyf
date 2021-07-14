package com.example.tyfserver.donation.exception;

import com.example.tyfserver.common.exception.BaseException;

public class DonationRequestException extends BaseException {

    private static final String errorCode = "donation-001";
    private static final String message = "후원 생성 시에 넘기는 Request의 값이 유효한 값이 아닙니다.";

    public DonationRequestException() {
        super(errorCode, message);
    }
}
