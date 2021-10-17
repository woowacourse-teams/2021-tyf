package com.example.tyfserver.payment.exception;

import com.example.tyfserver.common.exception.BaseException;

public class IllegalPaymentInfoException extends BaseException {

    public static final String ERROR_CODE_NOT_PAID = "payment-002";
    private static final String MESSAGE_NOT_PAID = "결제가 완료되지 않았습니다.";

    public static final String ERROR_CODE_INVALID_MERCHANT_ID = "payment-003";
    private static final String MESSAGE_INVALID_MERCHANT_ID = "결제 정보의 Merchant ID가 일치하지 않습니다.";

    public static final String ERROR_CODE_INVALID_AMOUNT = "payment-004";
    private static final String MESSAGE_INVALID_AMOUNT = "결제 정보의 Amount가 일치하지 않습니다.";

    public static final String ERROR_CODE_INVALID_CREATOR = "payment-005";
    private static final String MESSAGE_INVALID_CREATOR = "결제 정보의 PageName(상품정보)가 일치하지 않습니다.";

    public static final String ERROR_CODE_NOT_CANCELLED = "payment-006";
    private static final String MESSAGE_NOT_CANCELLED = "환불이 완료되지 않았습니다.";

    private static final String MESSAGE_INVALID_ERROR_CODE = "적절하지 않은 IllegalPaymentInfoException ErrorCode 입니다.";

    private IllegalPaymentInfoException(String code, String message) {
        super(code, message);
    }

    public static IllegalPaymentInfoException from(String code, String module) {
        if (ERROR_CODE_NOT_PAID.equals(code)) {
            return new IllegalPaymentInfoException(code, module + MESSAGE_NOT_PAID);
        }

        if (ERROR_CODE_INVALID_MERCHANT_ID.equals(code)) {
            return new IllegalPaymentInfoException(code, module + MESSAGE_INVALID_MERCHANT_ID);
        }

        if (ERROR_CODE_INVALID_AMOUNT.equals(code)) {
            return new IllegalPaymentInfoException(code, module + MESSAGE_INVALID_AMOUNT);
        }

        if (ERROR_CODE_INVALID_CREATOR.equals(code)) {
            return new IllegalPaymentInfoException(code, module + MESSAGE_INVALID_CREATOR);
        }

        if (ERROR_CODE_NOT_CANCELLED.equals(code)) {
            return new IllegalPaymentInfoException(code, module + MESSAGE_NOT_CANCELLED);
        }

        throw new RuntimeException(MESSAGE_INVALID_ERROR_CODE);
    }
}
