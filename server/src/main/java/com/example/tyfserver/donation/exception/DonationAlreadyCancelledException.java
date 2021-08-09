package com.example.tyfserver.donation.exception;

import com.example.tyfserver.common.exception.BaseException;

public class DonationAlreadyCancelledException extends BaseException {

    public static final String ERROR_CODE = "donation-004";
    private static final String MESSAGE = "이미 취소된 후원입니다.";

    public DonationAlreadyCancelledException() {
        super(ERROR_CODE, MESSAGE);
    }
}
