package com.example.tyfserver.donation.domain;

import com.example.tyfserver.donation.dto.DonationMessageRequest;

public class DonationTest {

    public static final String MESSAGE = "응원합니다";
    public static final long DONATION_AMOUNT = 1000L;

    public static DonationMessageRequest testMessageRequest() {
        return new DonationMessageRequest(MESSAGE, false);
    }

    public static DonationMessageRequest testSecretMessageRequest() {
        return new DonationMessageRequest(MESSAGE, true);
    }
}
