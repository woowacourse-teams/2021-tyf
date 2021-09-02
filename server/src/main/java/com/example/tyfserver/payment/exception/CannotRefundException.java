package com.example.tyfserver.payment.exception;

import com.example.tyfserver.common.exception.BaseException;
import com.example.tyfserver.donation.domain.DonationStatus;
import com.example.tyfserver.payment.domain.PaymentStatus;

public class CannotRefundException extends BaseException {

    public static final String ERROR_CODE = "payment-013";
    private static final String MESSAGE = "환불할 수 없는 결제 건입니다.";

    public CannotRefundException(String message) {
        super(ERROR_CODE, MESSAGE + message);
    }

    public CannotRefundException(DonationStatus donationStatus) {
        this("후원이 " + donationStatus.getInformation() + "상태입니다.");
    }

    public CannotRefundException(PaymentStatus paymentStatus) {
        this("결제가 " + paymentStatus.getInformation() + "상태입니다.");
    }

    public CannotRefundException() {
        this(MESSAGE);
    }
}
