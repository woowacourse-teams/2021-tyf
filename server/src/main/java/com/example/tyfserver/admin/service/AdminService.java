package com.example.tyfserver.admin.service;

import com.example.tyfserver.admin.domain.AdminAccount;
import com.example.tyfserver.admin.dto.AccountRejectRequest;
import com.example.tyfserver.admin.dto.AdminLoginRequest;
import com.example.tyfserver.admin.dto.ExchangeResponse;
import com.example.tyfserver.admin.dto.RequestingAccountResponse;
import com.example.tyfserver.admin.exception.ExchangeDoesNotAppliedException;
import com.example.tyfserver.admin.exception.NotRegisteredAccountException;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.service.AuthenticationService;
import com.example.tyfserver.common.util.Aes256Util;
import com.example.tyfserver.common.util.S3Connector;
import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.domain.Exchange;
import com.example.tyfserver.member.domain.ExchangeStatus;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.dto.ExchangeAmountDto;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.ExchangeRepository;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final ExchangeRepository exchangeRepository;
    private final DonationRepository donationRepository;
    private final MemberRepository memberRepository;
    private final SmtpMailConnector mailConnector;
    private final S3Connector s3Connector;
    private final SmtpMailConnector smtpMailConnector;
    private final AdminAccount adminAccount;
    private final AuthenticationService authenticationService;
    private final Aes256Util aes256Util;

    public void approveAccount(Long memberId) {
        Member member = findMember(memberId);
        member.approveAccount();
        s3Connector.delete(member.getBankBookUrl());
        smtpMailConnector.sendAccountApprove(member.getEmail());
    }

    public void rejectAccount(Long memberId, AccountRejectRequest accountRejectRequest) {
        Member member = findMember(memberId);
        member.rejectAccount();
        smtpMailConnector.sendAccountReject(member.getEmail(), accountRejectRequest.getRejectReason());
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

    public List<RequestingAccountResponse> findRequestingAccounts() {
        List<Member> members = memberRepository.findRequestingAccounts();
        List<RequestingAccountResponse> requestingAccountResponses = new ArrayList<>();

        for (Member member : members) {
            Account account = member.getAccount();
            String decryptedAccountNumber = aes256Util.decrypt(account.getAccountNumber());
            requestingAccountResponses.add(new RequestingAccountResponse(member.getId(), member.getEmail(),
                    member.getNickname(), member.getPageName(), account.getAccountHolder(), decryptedAccountNumber,
                    account.getBank(), account.getBankbookUrl()));
        }

        return requestingAccountResponses;
    }

    public List<ExchangeResponse> exchangeList() {
        List<ExchangeResponse> exchangeResponses = new ArrayList<>();

        for (Exchange exchange : exchangeRepository.findByStatus(ExchangeStatus.WAITING)) {
            String decryptedAccountNumber = aes256Util.decrypt(exchange.getMember().getAccount().getAccountNumber());
            exchangeResponses.add(new ExchangeResponse(exchange, decryptedAccountNumber));
        }

        return exchangeResponses;
    }

    // todo pageName말고 exchange id로 받으면 좋을듯.
    //  이 api전에 exchangeList() api가 호출되고 List<ExchangeResponse>를 반환하는데, 이때 exchangeId 주면 됨
    //  rejectExchange() 도 마찬가지.
    public void approveExchange(String pageName) {
        Member member = findMember(pageName);
        validateRegisteredAccount(member);
        Exchange exchange = findExchangeToApprove(member);

        List<Donation> donations = donationRepository.findDonationsToExchange(member, exchange.getExchangeOn());
        validateAmount(exchange, donations);

        exchange.toApproved();
        donations.forEach(Donation::toExchanged);

        mailConnector.sendExchangeApprove(member.getEmail());
    }

    public void rejectExchange(String pageName, String rejectReason) {
        Member member = findMember(pageName);
        validateRegisteredAccount(member);
        Exchange exchange = findExchangeToApprove(member);
        exchange.toRejected();

        mailConnector.sendExchangeReject(member.getEmail(), rejectReason);
    }

    private void validateRegisteredAccount(Member member) {
        if (member.isAccountNotRegistered()) {
            throw new NotRegisteredAccountException();
        }
    }

    private Exchange findExchangeToApprove(Member member) {
        List<Exchange> exchanges = exchangeRepository.findByStatusAndMember(ExchangeStatus.WAITING, member);
        if (exchanges.isEmpty()) {
            throw new ExchangeDoesNotAppliedException();
        }
        if (exchanges.size() > 1) {
            throw new RuntimeException("서버오류: 대기중인 정산이 2개 이상임"); // todo 클라이언트에게 예외 포맷대로 알려주기
        }
        return exchanges.get(0);
    }

    private void validateAmount(Exchange exchange, List<Donation> donations) {
        long actualTotalDonationPoint = donations.stream()
                .mapToLong(Donation::getPoint)
                .sum();

        if (exchange.getExchangeAmount() != actualTotalDonationPoint) {
            // todo 실제후원금액대로 정산을 진행시켜야하나?
            throw new RuntimeException("서버오류: 정산신청 금액과 실제 후원금액이 맞지 않음");
        }
    }

    private Member findMember(String pageName) {
        return memberRepository.findByPageName(pageName)
                .orElseThrow(MemberNotFoundException::new);
    }

    public TokenResponse login(AdminLoginRequest adminLoginRequest) {
        adminAccount.validateLogin(adminLoginRequest.getId(), adminLoginRequest.getPassword());
        String token = authenticationService.createAdminToken(adminLoginRequest.getId());
        return new TokenResponse(token);
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void updateExchangeAmount() {
        List<ExchangeAmountDto> exchangeAmountDtos = exchangeRepository.calculateExchangeAmountFromDonation(YearMonth.now());

        Map<Long, Long> idAmountMap = exchangeAmountDtos.stream()
                .collect(Collectors.toMap(ExchangeAmountDto::getExchangeId, ExchangeAmountDto::getExchangeAmount));

        exchangeRepository.findAllById(idAmountMap.keySet())
                .forEach(exchange -> exchange.updateExchangeAmount(idAmountMap.get(exchange.getId())));
    }
}
