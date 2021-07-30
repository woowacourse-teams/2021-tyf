package com.example.tyfserver.payment.util;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Component
public class UUIDValidator implements ConstraintValidator<UUID, String> {

    private final Pattern pattern = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

    @Override
    public void initialize(UUID UUID) {
    }

    @Override
    public boolean isValid(String merchantUid, ConstraintValidatorContext context) {
        return pattern.matcher(merchantUid).matches();
    }
}