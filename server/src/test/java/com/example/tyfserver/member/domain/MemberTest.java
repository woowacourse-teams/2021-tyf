package com.example.tyfserver.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.payment.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

    public static Member testMember() {
        return new Member("tyf@gmail.com", "nickname", "pageName", Oauth2Type.NAVER);
    }

    public static Member testMember2() {
        return new Member("tyf2@gmail.com", "nickname2", "pageName2", Oauth2Type.NAVER);
    }

    public static Member testMemberWithProfileImage() {
        return new Member("tyf@gmail.com", "nickname", "pageName", Oauth2Type.NAVER, "https://test.com/test");
    }

    @Test
    @DisplayName("addDonation 메서드 테스트")
    public void addDonationTest() {
        //given
        Member member = testMember();
        Donation donation = new Donation(new Payment(1000L, "test@test.com", "test"), new Message("name", "message", false));
        //when
        member.addDonation(donation);
        //then
        assertThat(member.getPoint()).isEqualTo(1000L);
    }

    @Test
    @DisplayName("updateBio 메서드 테스트")
    public void updateBioTest() {
        //given
        Member member = testMember();
        String bio = "안녕하세요! 로키입니다.";

        //when
        member.updateBio(bio);

        //then
        assertThat(member.getBio()).isEqualTo(bio);
    }
}
