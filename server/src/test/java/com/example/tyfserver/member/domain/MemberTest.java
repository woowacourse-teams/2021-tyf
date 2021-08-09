package com.example.tyfserver.member.domain;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.member.exception.AccountRegisteredException;
import com.example.tyfserver.member.exception.AccountRequestingException;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.exception.IllegalPaymentInfoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.tyfserver.payment.exception.IllegalPaymentInfoException.ERROR_CODE_NOT_PAID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("updateNickName 메서드 테스트")
    public void updateNickNameTest() {
        //given
        Member member = testMember();
        String nickName = "로키";

        //when
        member.updateNickName(nickName);

        //then
        assertThat(member.getNickname()).isEqualTo(nickName);
    }

    @Test
    @DisplayName("계좌정보를 등록한다.")
    public void registerAccount() {
        //given
        Member member = testMember();
        Account account = new Account("테스트", "1234-5678-1234", "https://test.com/test.png");
        member.registerAccount(account);

        assertThat(member.getAccountStatus()).isEqualTo(AccountStatus.REQUESTING);
    }

    @Test
    @DisplayName("계좌정보가 등록요청 상태이면 새로 계좌를 등록하지 못한다.")
    public void registerAccountWhenRequesting() {
        //given
        Member member = testMember();
        Account account = new Account("테스트", "1234-5678-1234", "https://test.com/test.png");
        member.registerAccount(account);

        assertThatThrownBy(() -> member.registerAccount(account))
                .isExactlyInstanceOf(AccountRequestingException.class);
    }
}
