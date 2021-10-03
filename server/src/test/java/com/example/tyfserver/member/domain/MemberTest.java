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

    private static final String EMAIL = "tyf@gmail.com";
    private static final String NICKNAME = "nickname";
    private static final String PAGE_NAME = "pageName";
    private static final String PROFILE_IMAGE = "https://test.com/profile.jpg";
    private static final String ACCOUNT_HOLDER = "홍길동";
    private static final String ACCOUNT_NUMBER = "1234-5678-1234";
    private static final String BANK = "하나";
    private static final String BANK_BOOK_URL = "https://test.com/bankbook.jpg";

    public static Member testMember() {
        return new Member(EMAIL, NICKNAME, PAGE_NAME, Oauth2Type.NAVER);
    }

    public static Member testMember(int i) {
        return new Member(EMAIL + i, NICKNAME + i, PAGE_NAME + i, Oauth2Type.NAVER);
    }

    public static Member testMemberWithAvailablePoint(long availablePoint) {
        return new Member(EMAIL, NICKNAME, PAGE_NAME, Oauth2Type.NAVER, PROFILE_IMAGE, new Point(availablePoint));
    }

    public static Member testMemberWithAccount(int i, AccountStatus accountStatus) {
        Member member = new Member(EMAIL + i, NICKNAME + i, PAGE_NAME + i, Oauth2Type.NAVER, PROFILE_IMAGE);
        member.addInitialAccount(new Account(ACCOUNT_HOLDER, ACCOUNT_NUMBER + i, BANK_BOOK_URL, BANK, accountStatus));
        return member;
    }

    @Test
    @DisplayName("addDonation 메서드 테스트")
    public void addDonationTest() {
        //given
        Member member = testMember();
        Donation donation = new Donation(new Message("name", "message", false), 1_000L);

        //when
        member.receiveDonation(donation);

        //then
        assertThat(member.getReceivedDonations()).isNotEmpty();
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
        member.registerAccount(ACCOUNT_HOLDER, ACCOUNT_NUMBER, BANK, BANK_BOOK_URL);

        //then
        Account memberAccount = member.getAccount();
        assertThat(memberAccount.getStatus()).isEqualTo(AccountStatus.REQUESTING);
        assertThat(memberAccount.getAccountHolder()).isEqualTo(ACCOUNT_HOLDER);
        assertThat(memberAccount.getAccountNumber()).isEqualTo(ACCOUNT_NUMBER);
        assertThat(memberAccount.getBank()).isEqualTo(BANK);
        assertThat(memberAccount.getBankbookUrl()).isEqualTo(BANK_BOOK_URL);

    }

    @Test
    @DisplayName("계좌정보가 등록요청 상태이면 새로 계좌를 등록하지 못한다.")
    public void registerAccountWhenRequesting() {
        //given
        Member member = generateMemberWithAccount();
        member.registerAccount(ACCOUNT_HOLDER, ACCOUNT_NUMBER, BANK, BANK_BOOK_URL);

        assertThatThrownBy(() -> member.registerAccount(ACCOUNT_HOLDER, ACCOUNT_NUMBER,
                BANK, BANK_BOOK_URL))
                .isExactlyInstanceOf(AccountRequestingException.class);
    }

    @Test
    @DisplayName("계좌정보가 등록이 승인된다.")
    public void approveAccount() {
        //given
        Member member = generateMemberWithAccount();
        member.registerAccount(ACCOUNT_HOLDER, ACCOUNT_NUMBER, BANK, BANK_BOOK_URL);

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
        member.registerAccount(ACCOUNT_HOLDER, ACCOUNT_NUMBER, BANK, BANK_BOOK_URL);

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
