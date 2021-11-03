package com.thankyou_for.donation.exception;

import com.thankyou_for.common.exception.BaseException;

public class DonationNotFoundException extends BaseException {

    public static final String ERROR_CODE = "donation-003";
    private static final String MESSAGE = "해당 후원을 찾을 수 없습니다.";

    public DonationNotFoundException() {
        super(ERROR_CODE, MESSAGE);
    }
}
