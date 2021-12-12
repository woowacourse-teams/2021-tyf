package com.thankyou_for.payment.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

class ResidentRegistrationNumberValidatorTest {

    @Mock
    private ConstraintValidatorContext mockContext;

    @ParameterizedTest(name = "ResidentRegistrationNumber 검증 실패")
    @ValueSource(strings = {"900101-100000", "900101-10000001", "900001-1000000", "900100-1000000", "900101-5000000"})
    @NullAndEmptySource
    void isValid_fail(String residentRegistrationNumberValidator) {
        // given
        ResidentRegistrationNumberValidator validator = new ResidentRegistrationNumberValidator();

        // when
        boolean actual = validator.isValid(residentRegistrationNumberValidator, mockContext);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("ResidentRegistrationNumber 검증 성공")
    @Test
    void isValid() {
        // given
        ResidentRegistrationNumberValidator validator = new ResidentRegistrationNumberValidator();
        String residentRegistrationNumberValidator = "900101-1000000";

        // when
        boolean actual = validator.isValid(residentRegistrationNumberValidator, mockContext);

        // then
        assertThat(actual).isTrue();
    }
}
