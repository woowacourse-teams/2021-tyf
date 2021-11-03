package com.thankyou_for.member.repository;

import com.thankyou_for.donation.domain.Donation;
import com.thankyou_for.donation.domain.DonationTest;
import com.thankyou_for.donation.repository.DonationRepository;
import com.thankyou_for.member.domain.*;
import com.thankyou_for.member.dto.ExchangeAmountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import supports.RepositoryTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class ExchangeRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ExchangeRepository exchangeRepository;
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AccountRepository accountRepository;

    private Exchange exchange;
    private Member member;

    @BeforeEach
    void setUp() {
        member = initMember(1);
        exchange = initExchange(10000L, member);

        Member member2 = initMember(2);
        Exchange exchange2 = initExchange(15000L, member2);
        exchange2.toApproved();

        Member member3 = initMember(3);
        Exchange exchange3 = initExchange(20000L, member3);
        exchange3.toRejected();

        Exchange exchange4 = initExchange(10000L, member);
        exchange4.toApproved();
    }

    private Member initMember(int i) {
        Member member = MemberTest.testMemberWithAccount(i, AccountStatus.REGISTERED);
        accountRepository.save(member.getAccount());
        return memberRepository.save(member);
    }

    private Exchange initExchange(long amount, Member member) {
        Exchange exchange = new Exchange(amount, YearMonth.of(2021, 1), member);
        return exchangeRepository.save(exchange);
    }

    private Exchange initExchange(long amount, Member member, YearMonth exchangeOn) {
        Exchange exchange = new Exchange(amount, exchangeOn, member);
        return exchangeRepository.save(exchange);
    }

    private Donation initDonation(LocalDateTime createdAt) {
        Donation donation = new Donation(DonationTest.testMessage(), 5000, createdAt);
        return donationRepository.save(donation);
    }

    private void flushAndClear() {
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("ExchangeStatus와 Member로 Exchange 조회")
    void findByStatusAndMember() {
        //when
        flushAndClear();
        List<Exchange> exchanges = exchangeRepository.findByStatusAndMember(ExchangeStatus.WAITING, exchange.getMember());

        //then
        assertThat(exchanges).hasSize(1);
        Exchange exchange = exchanges.get(0);
        assertThat(exchange.getMember().getId()).isEqualTo(member.getId());
        assertThat(exchange.getExchangeAmount()).isEqualTo(10000);
    }

    @Test
    @DisplayName("정산해야 하는 도네이션들의 총 정산금액을 계산한다.")
    public void calculateExchangeAmountFromDonation() {
        //given
        Member member = initMember(5);
        member.receiveDonation(initDonation(createdAt(1, 1)));
        member.receiveDonation(initDonation(createdAt(2, 1)));
        member.receiveDonation(initDonation(LocalDateTime.of(2021, 2, 28, 23, 59)));

        member.receiveDonation(initDonation(createdAt(3, 1)));
        member.receiveDonation(initDonation(createdAt(12, 31)));
        Donation donation = initDonation(createdAt(1, 1));
        donation.toExchanged();
        member.receiveDonation(donation);

        Exchange exchange = initExchange(0L, member, YearMonth.of(2021, 1));

        //when
        flushAndClear();
        List<ExchangeAmountDto> exchangeAmountDtos = exchangeRepository.calculateExchangeAmountFromDonation(YearMonth.of(2021, 3));
        flushAndClear();

        //then
        assertThat(exchangeAmountDtos).hasSize(1);
        assertThat(exchangeAmountDtos.get(0).getExchangeId()).isEqualTo(exchange.getId());
        assertThat(exchangeAmountDtos.get(0).getExchangeAmount()).isEqualTo(15000);
    }

    private LocalDateTime createdAt(int month, int dayOfMonth) {
        return LocalDate.of(2021, month, dayOfMonth).atStartOfDay();
    }
}
