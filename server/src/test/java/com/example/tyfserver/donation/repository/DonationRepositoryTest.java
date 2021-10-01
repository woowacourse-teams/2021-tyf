package com.example.tyfserver.donation.repository;

import com.example.tyfserver.common.config.JpaAuditingConfig;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.DonationStatus;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class DonationRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private Member member1;
    private Member member2;
    private Donation donation1;
    private Donation donation2;
    private Donation donation3;
    private Donation donation4;
    private Donation donation5;
    private Donation donation6;
    private Donation donation7;

    @BeforeEach
    void setUp() {
        member1 = MemberTest.testMember(1);
        member2 = MemberTest.testMember(2);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 총 28000 포인트
        donation1 = initDonation(1000L, false);
        donation2 = initDonation(2000L, false);
        donation3 = initDonation(3000L, false);
        donation4 = initDonation(4000L, false);
        donation5 = initDonation(5000L, true);
        donation6 = initDonation(6000L, false);
        donation7 = initDonation(7000L, false);
    }

    private Donation initDonation(Long point, boolean secret) {
        Donation donation = new Donation(new Message("name", "message", secret), point);
        member1.addDonation(donation);
        donationRepository.save(donation);
        return donation;
    }

    private Donation initDonation(long point, Member member, LocalDateTime createAt) {
        Donation donation = new Donation(new Message("name", "message", false), point, createAt);
        member.addDonation(donation);
        donationRepository.save(donation);
        return donation;
    }

    @Test
    @DisplayName("해당 Member가 받은 secret false인 최신 5개의 도네이션을 가져온다.")
    public void findPublicDonations() {
        List<Donation> donations = donationRepository.findPublicDonations(member1, false, PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt")));
        assertThat(donations).containsExactlyInAnyOrder(
                donation7, donation2, donation3, donation4, donation6
        );
    }

    @Test
    @DisplayName("해당 Member가 받은 최신 5개의 도네이션을 가져온다.")
    public void findTop5ByMember() {
        List<Donation> donations = donationRepository.findFirst5ByMemberAndStatusNotOrderByCreatedAtDesc(member1, DonationStatus.CANCELLED);
        assertThat(donations).containsExactlyInAnyOrder(
                donation7, donation6, donation5, donation4, donation3
        );
    }

    @Test
    @DisplayName("해당 Member가 받은 최신 도네이션을 가져온다. size 3에 두 번째 page인 경우")
    public void findDonationByMemberOrderByCreatedAtDesc() {
        List<Donation> donations = donationRepository.findDonationByMemberAndStatusNotOrderByCreatedAtDesc(
                member1, DonationStatus.CANCELLED, PageRequest.of(1, 3));
        assertThat(donations).containsExactlyInAnyOrder(
                donation4, donation3, donation2
        );
    }

    @Test
    @DisplayName("정산 가능 포인트를 조회한다.")
    public void exchangeablePoint() {
        donation1.toCancelled();
        donation2.toExchanged();
        donation3.toCancelled();
        donation4.toCancelled();
        donation5.toExchangeable();
        donation6.toExchangeable();

        Long member1ExchangeablePoint = donationRepository.exchangeablePoint(member1.getId());
        Long member2ExchangeablePoint = donationRepository.exchangeablePoint(member2.getId());

        assertThat(member1ExchangeablePoint).isEqualTo(11000L);
        assertThat(member2ExchangeablePoint).isEqualTo(0L);
    }

    @Test
    @DisplayName("정산 완료 총 포인트를 조회한다.")
    public void exchangedTotalPoint() {
        donation1.toCancelled();
        donation2.toExchanged();
        donation3.toCancelled();
        donation4.toCancelled();

        Long exchangedTotalPoint = donationRepository.exchangedTotalPoint(member1.getId());

        assertThat(exchangedTotalPoint).isEqualTo(2000L);
    }

    @Test
    @DisplayName("정산 가능 + 정산 불가 총 포인트를 조회한다.")
    public void currentPoint() {
        donation1.toExchanged();
        donation2.toExchanged();

        Long exchangedTotalPoint = donationRepository.currentPoint(member1.getId());

        assertThat(exchangedTotalPoint).isEqualTo(25000L);
    }

    @Test
    @DisplayName("정산해야하는 도네이션들을 조회한다.")
    void findDonationsToExchange() {
        // given
        Member member3 = MemberTest.testMember(3);
        Member member4 = MemberTest.testMember(4);
        memberRepository.save(member3);
        memberRepository.save(member4);

        Donation donation1 = initDonation(5000L, member3, createdAt(1, 1));
        Donation donation2 = initDonation(5000L, member4, createdAt(1, 1));
//        Donation donation3 = initDonation(5000L, member3, createdAt(2, 1));
//        Donation donation4 = initDonation(5000L, member4, createdAt(2, 1));

        Donation donation5 = initDonation(5000L, member3, createdAt(1, 31));

        // when
        List<Donation> donations = donationRepository.findDonationsToExchange(member3, YearMonth.of(2021, 2));

        // then
        assertThat(donations).containsExactlyInAnyOrder(donation1, donation5);
    }

    private LocalDateTime createdAt(int month, int dayOfMonth) {
        return LocalDateTime.of(2021, month, dayOfMonth, 0, 0);
    }
}
