package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.DonationStatus;
import com.example.tyfserver.donation.domain.DonationTest;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import supports.RepositoryTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class DonationRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DonationRepository donationRepository;

    private Member creator;
    private Member donator;
    private Donation donation1;
    private Donation donation2;
    private Donation donation3;
    private Donation donation4;
    private Donation donation5;
    private Donation donation6;
    private Donation donation7;

    @BeforeEach
    void setUp() {
        creator = initMember(1);
        donator = initMember(2);
        donator.increasePoint(100000L);

        // 총 28000 포인트
        donation1 = initDonation(1000L, false);
        donation2 = initDonation(2000L, false);
        donation3 = initDonation(3000L, false);
        donation4 = initDonation(4000L, false);
        donation5 = initDonation(5000L, true);
        donation6 = initDonation(6000L, false);
        donation7 = initDonation(7000L, false);
    }

    private Member initMember(int i) {
        Member member = MemberTest.testMember(i);
        return memberRepository.save(member);
    }

    private Donation initDonation(Long point, boolean secret) {
        Donation donation = new Donation(DonationTest.testMessage(secret), point);
        donation.donate(donator, creator);
        return donationRepository.save(donation);
    }

    private Donation initDonation(Member creator, LocalDateTime createAt) {
        return initDonation(creator, createAt, DonationStatus.WAITING_FOR_EXCHANGE);
    }

    private Donation initDonation(Member creator, LocalDateTime createAt, DonationStatus status) {
        Donation donation = new Donation(DonationTest.testMessage(), 5000, status, createAt);
        creator.receiveDonation(donation);
        return donationRepository.save(donation);
    }

    @Test
    @DisplayName("해당 Member가 받은 최신 5개의 도네이션을 가져온다.")
    public void find5NewerDonationPage() {
        List<Donation> donations = donationRepository
                .find5NewerDonationPage(creator, 0L);

        assertThat(donations).containsExactly(donation7, donation6, donation5, donation4, donation3);
    }

    @Test
    @DisplayName("해당 Member가 받은 도네이션을 donation7부터 최신순으로 5개 가져온다.")
    public void find5NewerDonationPage_2() {
        List<Donation> donations = donationRepository
                .find5NewerDonationPage(creator, donation7.getId());

        assertThat(donations).containsExactly(donation6, donation5, donation4, donation3, donation2);
    }

    @Test
    @DisplayName("정산되지 않은 총 포인트를 조회한다.")
    public void waitingTotalPoint() {
        donation1.toExchanged();
        donation2.toExchanged();
        donation3.toExchanged();
        donation4.toExchanged();
        donation7.toExchanged();

        Long waitingTotalPoint1 = donationRepository.waitingTotalPoint(creator.getId());
        Long waitingTotalPoint2 = donationRepository.waitingTotalPoint(donator.getId());

        assertThat(waitingTotalPoint1).isEqualTo(11000L);
        assertThat(waitingTotalPoint2).isEqualTo(0L);
    }

    @Test
    @DisplayName("정산이 완료된 총 포인트를 조회한다.")
    public void exchangedTotalPoint() {
        donation2.toExchanged();

        Long exchangedTotalPoint = donationRepository.exchangedTotalPoint(creator.getId());

        assertThat(exchangedTotalPoint).isEqualTo(2000L);
    }

    @Test
    @DisplayName("정산해야하는 도네이션들을 조회한다.")
    public void findDonationsToExchange() {
        // given
        Member creator1 = initMember(3);
        Member creator2 = initMember(4);

        Donation donation1 = initDonation(creator1, createdAt(1, 1));
        Donation donation2 = initDonation(creator1, createdAt(2, 1));
        Donation donation3 = initDonation(creator1, LocalDateTime.of(2021, 2, 28, 23, 59));

        initDonation(creator2, createdAt(1, 1));
        initDonation(creator1, createdAt(3, 1));
        initDonation(creator1, createdAt(12, 31));
        initDonation(creator1, createdAt(1, 1), DonationStatus.EXCHANGED);


        // when
        List<Donation> donations = donationRepository.findDonationsToExchange(creator1, YearMonth.of(2021, 2));

        // then
        List<Long> expectedDonationIds = Stream.of(donation1, donation2, donation3)
                .map(Donation::getId)
                .collect(Collectors.toList());

        assertThat(donations).hasSize(expectedDonationIds.size());
        donations.forEach(donation -> assertThat(donation.getId()).isIn(expectedDonationIds));
    }

    private LocalDateTime createdAt(int month, int dayOfMonth) {
        return LocalDate.of(2021, month, dayOfMonth).atStartOfDay();
    }
}
