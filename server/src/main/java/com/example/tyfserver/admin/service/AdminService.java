package com.example.tyfserver.admin.service;

import com.example.tyfserver.admin.dto.AccountCancelRequest;
import com.example.tyfserver.common.util.S3Connector;
import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void cancelAccount(Long memberId, AccountCancelRequest accountCancelRequest) {
        Member member = findMember(memberId);
        member.cancelAccount();
        smtpMailConnector.sendAccountCancel(member.getEmail(), accountCancelRequest.getCancelReason());
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }
}
