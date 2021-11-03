package com.thankyou_for.payment.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ResidentRegistrationNumberValidator implements ConstraintValidator<ResidentRegistrationNumber, String> {

    private final Pattern pattern = Pattern.compile("^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|[3][01])\\-[1-4][0-9]{6}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return pattern.matcher(value).matches();
    }
}
