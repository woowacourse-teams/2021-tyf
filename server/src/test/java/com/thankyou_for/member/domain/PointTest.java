package com.thankyou_for.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PointTest {

    @Test
    @DisplayName("Point객체의 add 메서드 test")
    public void pointAdd() {
        //given
        Point point = new Point(1000L);
        //when
        point.add(2000L);
        //then
        assertThat(point.getPoint()).isEqualTo(3000L);
    }

}