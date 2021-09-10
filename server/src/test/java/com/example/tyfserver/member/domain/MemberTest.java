package com.example.tyfserver.member.domain;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.member.exception.AccountRequestingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    public static Member testMemberWithAvailablePoint(long availablePoint) {
        return new Member("tyf@gmail.com", "nickname", "pageName", Oauth2Type.NAVER, "https://test.com/test", new Point(availablePoint));
    }

    @Test
    @DisplayName("addDonation 메서드 테스트")
    public void addDonationTest() {
        //given
        Member member = testMember();
        Donation donation = new Donation(new Message("name", "message", false), 1_000L);

        //when
        member.addDonation(donation);

        //then
        assertThat(member.getDonations()).isNotEmpty();
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
    @DisplayName("updateNickname 메서드 테스트")
    public void updateNicknameTest() {
        //given
        Member member = testMember();
        String nickname = "로키";

        //when
        member.updateNickname(nickname);

        //then
        assertThat(member.getNickname()).isEqualTo(nickname);
    }

    @Test
    @DisplayName("계좌정보를 등록한다.")
    public void registerAccount() {
        //given
        Member member = generateMemberWithAccount();

        //when
        member.registerAccount("테스트", "1234-5678-1234", "하나", "https://test.com/test.png");

        //then
        Account memberAccount = member.getAccount();
        assertThat(memberAccount.getStatus()).isEqualTo(AccountStatus.REQUESTING);
        assertThat(memberAccount.getAccountHolder()).isEqualTo("테스트");
        assertThat(memberAccount.getAccountNumber()).isEqualTo("1234-5678-1234");
        assertThat(memberAccount.getBank()).isEqualTo("하나");
        assertThat(memberAccount.getBankbookUrl()).isEqualTo("https://test.com/test.png");

    }

    @Test
    @DisplayName("계좌정보가 등록요청 상태이면 새로 계좌를 등록하지 못한다.")
    public void registerAccountWhenRequesting() {
        //given
        Member member = generateMemberWithAccount();
        member.registerAccount("테스트", "1234-5678-1234", "하나", "https://test.com/test.png");

        assertThatThrownBy(() -> member.registerAccount("테스트", "1234-5678-1234",
                "하나", "https://test.com/test.png"))
                .isExactlyInstanceOf(AccountRequestingException.class);
    }

    @Test
    @DisplayName("계좌정보가 등록이 승인된다.")
    public void approveAccount() {
        //given
        Member member = generateMemberWithAccount();
        member.registerAccount("테스트", "1234-5678-1234", "하나", "https://test.com/test.png");

        //when
        member.approveAccount();

        //then
        Account memberAccount = member.getAccount();
        assertThat(memberAccount.getStatus()).isEqualTo(AccountStatus.REGISTERED);
    }

    @Test
    @DisplayName("계좌정보가 등록이 반려된다.")
    public void cancelAccount() {
        //given
        Member member = generateMemberWithAccount();
        member.registerAccount("테스트", "1234-5678-1234", "하나", "https://test.com/test.png");

        //when
        member.rejectAccount();

        //then
        Account memberAccount = member.getAccount();
        assertThat(memberAccount.getStatus()).isEqualTo(AccountStatus.REJECTED);
    }

    private Member generateMemberWithAccount() {
        Member member = testMember();
        member.addInitialAccount(new Account());
        return member;
    }
}
