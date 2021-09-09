package com.example.tyfserver.payment.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ItemValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Enum {
    String message() default "유효하지 않은 Item 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends java.lang.Enum<?>> enumClass();
    boolean ignoreCase() default true;
}
