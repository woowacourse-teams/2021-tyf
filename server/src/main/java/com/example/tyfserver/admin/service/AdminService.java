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
import com.example.tyfserver.donation.domain.DonationStatus;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.domain.Exchange;
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
        List<Exchange> exchanges = exchangeRepository.findAll();
        List<ExchangeResponse> exchangeResponses = new ArrayList<>();

        for (Exchange exchange : exchanges) {
            String decryptedAccountNumber = aes256Util.decrypt(exchange.getAccountNumber());
            exchangeResponses.add(new ExchangeResponse(exchange.getName(), exchange.getEmail(), exchange.getExchangeAmount(),
                    decryptedAccountNumber, exchange.getNickname(), exchange.getPageName(), exchange.getCreatedAt()));
        }

        return exchangeResponses;
    }

    public void approveExchange(String pageName) {
        Member member = findMember(pageName);
        List<Donation> donations = donationRepository.findDonationByStatusAndCreator(DonationStatus.WAITING_FOR_EXCHANGE, member);
        for (Donation donation : donations) {
            donation.toExchanged();
        }
        mailConnector.sendExchangeApprove(member.getEmail());
        exchangeRepository.deleteByPageName(pageName);
    }

    public void rejectExchange(String pageName, String rejectReason) {
        Member member = findMember(pageName);
        exchangeRepository.deleteByPageName(pageName);
        mailConnector.sendExchangeReject(member.getEmail(), rejectReason);
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
