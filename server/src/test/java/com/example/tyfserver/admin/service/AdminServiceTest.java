package com.example.tyfserver.admin.service;

import com.example.tyfserver.admin.dto.ExchangeResponse;
import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.DonationStatus;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Exchange;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.ExchangeRepository;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ExchangeRepository exchangeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private DonationRepository donationRepository;
    @MockBean
    private SmtpMailConnector mailConnector;

    @Test
    @DisplayName("정산 목록 조회")
    public void exchangeList() {
        Exchange exchange1 = new Exchange(12000L, "123-123-123", "nickname1", "pagename1");
        Exchange exchange2 = new Exchange(21000L, "456-456-456", "nickname2", "pagename2");
        Exchange exchange3 = new Exchange(31000L, "789-789-789", "nickname3", "pagename3");
        exchangeRepository.save(exchange1);
        exchangeRepository.save(exchange2);
        exchangeRepository.save(exchange3);

        List<ExchangeResponse> responses = adminService.exchangeList();
        assertThat(responses.get(0)).usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(new ExchangeResponse(12000L, "123-123-123", "nickname1", "pagename1", LocalDateTime.now()));
    }

    @Test
    @DisplayName("정산 승인")
    public void approveExchange() {
        //given
        Member member = new Member("tyf@gmail.com", "nickname", "pagename", Oauth2Type.KAKAO);
        memberRepository.save(member);
        Payment payment = paymentRepository.save(new Payment(13000L, "tyf@gmail.com", "pagename", UUID.randomUUID()));
        Donation donation = new Donation(payment);
        member.addDonation(donation);
        donation.toExchangeable();
        donationRepository.save(donation);

        Exchange exchange = new Exchange(13000L, "123-123", "nickname", "pagename");
        exchangeRepository.save(exchange);

        //when
        doNothing().when(mailConnector).sendExchangeResult(anyString(), anyString());
        adminService.approveExchange(member.getPageName());

        //then
        assertThat(donation.getStatus()).isEqualTo(DonationStatus.EXCHANGED);
        assertThat(exchangeRepository.existsByPageName("pagename")).isFalse();
    }

    @Test
    @DisplayName("정산 승인 - 회원을 찾을 수 없는 경우")
    public void approveExchangeMemberNotFound() {
        assertThatThrownBy(() -> adminService.approveExchange("any"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("정산 거절")
    public void rejectExchange() {
        //given
        Member member = new Member("tyf@gmail.com", "nickname", "pagename", Oauth2Type.KAKAO);
        memberRepository.save(member);
        Payment payment = paymentRepository.save(new Payment(13000L, "tyf@gmail.com", "pagename", UUID.randomUUID()));
        Donation donation = new Donation(payment);
        member.addDonation(donation);
        donation.toExchangeable();
        donationRepository.save(donation);

        Exchange exchange = new Exchange(13000L, "123-123", "nickname", "pagename");
        exchangeRepository.save(exchange);

        //when
        doNothing().when(mailConnector).sendExchangeResult(anyString(), anyString());
        adminService.rejectExchange(member.getPageName(), "그냥 거절하겠다. 토 달지 말아라");

        //then
        assertThat(exchangeRepository.existsByPageName(member.getPageName())).isFalse();
        assertThat(donation.getStatus()).isEqualTo(DonationStatus.EXCHANGEABLE);
    }

    @Test
    @DisplayName("정산 거절 - 회원을 찾을 수 없는 경우")
    public void rejectExchangeMemberNotFound() {
        assertThatThrownBy(() -> adminService.rejectExchange("any", "just denied"))
                .isInstanceOf(MemberNotFoundException.class);
    }
}