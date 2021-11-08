package com.thankyou_for.payment.util;

import java.math.BigDecimal;
import java.math.MathContext;

public class TaxIncludedCalculator {

    private static final double TAX_RATIO = 1.1;

    public static long addTax(long price) {
        return (long) (price * TAX_RATIO);
    }

    public static long detachTax(long price) {
        return BigDecimal.valueOf(price).divide(BigDecimal.valueOf(TAX_RATIO), MathContext.DECIMAL32).longValue();
    }
}
