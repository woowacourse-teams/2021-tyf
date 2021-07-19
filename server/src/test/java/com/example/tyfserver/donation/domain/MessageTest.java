package com.example.tyfserver.donation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTest {

    @Test
    @DisplayName("메세지 비공개 테스트")
    public void hideNameAndMessageMethodTest() {
        //given
        Message message = new Message("name", "message", true);
        //when
        message.hideNameAndMessage();
        //then
        assertThat(message.getName()).isEqualTo(Message.SECRET_NAME);
        assertThat(message.getMessage()).isEqualTo(Message.SECRET_MESSAGE);
    }

}