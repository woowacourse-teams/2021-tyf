package com.example.tyfserver.donation.domain;

import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DonationTest {

    public static final String NAME = "joy";
    public static final String MESSAGE = "응원합니다";
    public static final long DONATION_AMOUNT = 1000L;

    public static DonationMessageRequest testMessageRequest() {
        return new DonationMessageRequest(NAME, MESSAGE, false);
    }

    public static DonationMessageRequest testSecretMessageRequest() {
        return new DonationMessageRequest(NAME, MESSAGE, true);
    }

    public static DonationRequest testDonationRequest(Member member) {
        return new DonationRequest(member.getPageName(), DONATION_AMOUNT);
    }

    @Test
    @DisplayName("hideDonation 테스트")
    public void hideNameAndMessageWhenSecret() {
        //given
        Donation donation = new Donation(1000L, new Message("name", "message", true));
        //when
        donation.hideNameAndMessageWhenSecret();
        //then
        assertThat(donation.getName()).isEqualTo(Message.SECRET_NAME);
        assertThat(donation.getMessage()).isEqualTo(Message.SECRET_MESSAGE);
    }
}
