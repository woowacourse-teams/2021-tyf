package com.example.tyfserver.payment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @ParameterizedTest
    @DisplayName("itemId로 Item을 찾는 기능")
    @EnumSource(Item.class)
    void testFindItem(Item expected) {
        //given
        String itemId = expected.name();

        //when
        Item actual = Item.findItem(itemId);

        //then
        assertThat(actual).isSameAs(expected);
    }
}
