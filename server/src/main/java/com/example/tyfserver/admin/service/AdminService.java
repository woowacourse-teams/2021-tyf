package com.example.tyfserver.admin.service;

import com.example.tyfserver.admin.domain.AdminAccount;
import com.example.tyfserver.admin.dto.AccountRejectRequest;
import com.example.tyfserver.admin.dto.AdminLoginRequest;
import com.example.tyfserver.admin.dto.ExchangeResponse;
import com.example.tyfserver.admin.dto.RequestingAccountResponse;
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
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.ExchangeRepository;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public void approveExchange(String pageName) {
        Member member = findMember(pageName);
        Exchange exchange = findExchangeToApprove(member);

        List<Donation> donations = donationRepository.findDonationsToExchange(member, exchange.getExchangeOn());
        validateAmount(exchange, donations);

        exchange.toApproved();
        donations.forEach(Donation::toExchanged);

        mailConnector.sendExchangeApprove(member.getEmail());
    }

    public void rejectExchange(String pageName, String rejectReason) {
        Member member = findMember(pageName);
        Exchange exchange = findExchangeToApprove(member);
        exchange.toRejected();

        mailConnector.sendExchangeReject(member.getEmail(), rejectReason);
    }

    private Exchange findExchangeToApprove(Member member) {
        List<Exchange> exchanges = exchangeRepository.findByStatusAndMember(ExchangeStatus.WAITING, member);
        if (exchanges.isEmpty()) {
            throw new RuntimeException("사용자오류: 정산신청하지 않았음"); // todo Runtime 예외 제거
        }
        if (exchanges.size() > 1) {
            throw new RuntimeException("서버오류: 대기중인 정산이 2개 이상임");
        }
        return exchanges.get(0);
    }

    private void validateAmount(Exchange exchange, List<Donation> donations) {
        long donationAmountSum = donations.stream()
                .mapToLong(Donation::getPoint)
                .sum();

        if (exchange.getExchangeAmount() != donationAmountSum) {
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
}
