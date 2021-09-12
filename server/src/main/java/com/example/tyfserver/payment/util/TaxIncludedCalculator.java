package com.example.tyfserver.payment.util;

import java.math.BigDecimal;

public class TaxIncludedCalculator {

    private static final double TAX_RATIO = 1.1;

    public static long addTax(long price) {
        return (long) (price * TAX_RATIO);
    }

    public static long detachTax(long price) {
        return BigDecimal.valueOf(price).divide(BigDecimal.valueOf(TAX_RATIO)).longValue();
    }
}
