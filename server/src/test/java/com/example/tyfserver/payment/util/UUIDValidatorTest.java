package com.example.tyfserver.payment.util;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

class UUIDValidatorTest {

    @Mock
    private ConstraintValidatorContext mockContext;

    @Test
    void name() {
        // given
        UUIDValidator uuidValidator = new UUIDValidator();

        // when
        boolean valid = uuidValidator.isValid(null, mockContext);

        // then
        assertThat(valid).isFalse();
    }
}