package com.example.tyfserver.donation.exception;

import com.example.tyfserver.common.exception.BaseException;

public class DonationNotFoundException extends BaseException {

    private static final String errorCode = "donation-003";
    private static final String message = "해당 후원을 찾을 수 없습니다.";

    public DonationNotFoundException() {
        super(errorCode, message);
    }
}
