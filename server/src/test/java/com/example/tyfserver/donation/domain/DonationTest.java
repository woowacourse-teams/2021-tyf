package com.example.tyfserver.donation.domain;

public class DonationTest {

    private static final String MESSAGE = "응원합니다";

    public static Message testMessage() {
        return testMessage(false);
    }

    public static Message testMessage(boolean secret) {
        return new Message("test", MESSAGE, secret);
    }
}
