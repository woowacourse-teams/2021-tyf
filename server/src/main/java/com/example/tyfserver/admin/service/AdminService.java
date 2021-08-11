package com.example.tyfserver.admin.service;

import com.example.tyfserver.admin.dto.AccountRejectRequest;
import com.example.tyfserver.admin.dto.RequestingAccountResponse;
import com.example.tyfserver.common.util.S3Connector;
import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import com.example.tyfserver.admin.domain.AdminAccount;
import com.example.tyfserver.admin.dto.AdminLoginRequest;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final S3Connector s3Connector;
    private final SmtpMailConnector smtpMailConnector;

    public void approveAccount(Long memberId) {
        Member member = findMember(memberId);
        member.approveAccount();
        s3Connector.delete(member.getBankBookUrl());
        smtpMailConnector.sendAccountApprove(member.getEmail());
    }

    public void rejectAccount(Long memberId, AccountRejectRequest accountRejectRequest) {
        Member member = findMember(memberId);
        member.rejectAccount();
        smtpMailConnector.sendAccountCancel(member.getEmail(), accountRejectRequest.getRejectReason());
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

    public List<RequestingAccountResponse> findRequestingAccounts() {
        return RequestingAccountResponse.toList(memberRepository.findRequestingAccounts());
    }


    private final AdminAccount adminAccount;
    private final AuthenticationService authenticationService;

    public TokenResponse login(AdminLoginRequest adminLoginRequest) {
        adminAccount.validateLogin(adminLoginRequest.getId(), adminLoginRequest.getPassword());
        String token = authenticationService.createAdminToken(adminLoginRequest.getId());
        return new TokenResponse(token);
    }
}
