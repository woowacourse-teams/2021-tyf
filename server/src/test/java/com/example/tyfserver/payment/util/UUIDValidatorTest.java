package com.example.tyfserver.payment.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class UUIDValidatorTest {

    @Mock
    private ConstraintValidatorContext mockContext;

    @ParameterizedTest(name = "UUID 검증 실패")
    @MethodSource("isValid_fail_source")
    void isValid_fail(String uuid) {
        // given
        UUIDValidator uuidValidator = new UUIDValidator();

        // when
        boolean valid = uuidValidator.isValid(uuid, mockContext);

        // then
        assertThat(valid).isFalse();
    }

    private static Stream<Arguments> isValid_fail_source() {
        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(""),
                Arguments.of("invalid uuid format")
        );
    }

    @DisplayName("UUID 검증 성공")
    @Test
    void isValid() {
        // given
        UUIDValidator uuidValidator = new UUIDValidator();
        String uuid = UUID.randomUUID().toString();

        // when
        boolean valid = uuidValidator.isValid(uuid, mockContext);

        // then
        assertThat(valid).isTrue();
    }
}