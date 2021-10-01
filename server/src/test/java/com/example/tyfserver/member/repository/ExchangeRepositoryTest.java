package com.example.tyfserver.member.repository;

import com.example.tyfserver.admin.dto.ExchangeResponse;
import com.example.tyfserver.common.config.JpaAuditingConfig;
import com.example.tyfserver.member.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class ExchangeRepositoryTest {

    @Autowired
    private ExchangeRepository exchangeRepository;
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
        memberRepository.save(member);
        return member;
    }

    private Exchange initExchange(long amount, Member member) {
        Exchange exchange = new Exchange(amount, YearMonth.of(2021, 1), member);
        exchangeRepository.save(exchange);
        return exchange;
    }

    @Test
    @DisplayName("ExchangeStatus와 Member로 Exchange 조회")
    void findByStatusAndMember() {
        List<Exchange> exchanges = exchangeRepository
                .findByStatusAndMember(ExchangeStatus.WAITING, exchange.getMember());

        exchanges.stream()
                .map(exchange1 -> new ExchangeResponse(exchange1, "123123"))
                .forEach(exchangeResponse -> {
                    System.out.println(exchangeResponse.getEmail());
                    System.out.println(exchangeResponse.getAccountNumber());
                });

        assertThat(exchanges).hasSize(1);
        Exchange exchange = exchanges.get(0);
        assertThat(exchange.getMember()).isEqualTo(member);
        assertThat(exchange.getExchangeAmount()).isEqualTo(10000);
    }

    //    @Test
//    @DisplayName("페이지 네임으로 정산존재 확인")
//    public void existsByPageName() {
//        boolean result = exchangeRepository.existsByPageName(exchange.getMember().getPageName());
//        assertThat(result).isTrue();
//    }

//    @Test
//    @DisplayName("페이지 네임으로 정산존재 확인 - 못찾은 경우")
//    public void existsByPageNameNotFound() {
//        boolean result = exchangeRepository.existsByPageName("nope");
//        assertThat(result).isFalse();
//    }

//    @Test
//    @DisplayName("페이지 네임으로 정산 삭제")
//    public void deleteByPageName() {
//        boolean result = exchangeRepository.existsByPageName(exchange.getMember().getPageName());
//        assertThat(result).isTrue();
//
//        exchangeRepository.deleteByPageName(exchange.getMember().getPageName());
//        result = exchangeRepository.existsByPageName(exchange.getMember().getPageName());
//        assertThat(result).isFalse();
//    }
}