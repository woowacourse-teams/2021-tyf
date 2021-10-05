package com.example.tyfserver.donation.domain;

import com.example.tyfserver.donation.dto.DonationMessageRequest;

public class DonationTest {

    private static final String MESSAGE = "응원합니다";

    public static Message testMessage() {
        return testMessage(false);
    }

    public static Message testMessage(boolean secret) {
        return new Message("test", MESSAGE, secret);
    }

    public static DonationMessageRequest testMessageRequest() {
        return new DonationMessageRequest(MESSAGE, false);
    }

    public static DonationMessageRequest testSecretMessageRequest() {
        return new DonationMessageRequest(MESSAGE, true);
    }
}
