package com.thankyou_for.payment.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ResidentRegistrationNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResidentRegistrationNumber {

    String message() default "올바른 주민등록번호 형식이 아닙니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
