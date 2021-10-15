package com.example.tyfserver.member.service;

import com.example.tyfserver.admin.exception.NotRegisteredAccountException;
import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.common.util.Aes256Util;
import com.example.tyfserver.common.util.S3Connector;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.domain.Exchange;
import com.example.tyfserver.member.domain.ExchangeStatus;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.dto.*;
import com.example.tyfserver.member.exception.*;
import com.example.tyfserver.member.repository.ExchangeRepository;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final DonationRepository donationRepository;
    private final ExchangeRepository exchangeRepository;
    private final S3Connector s3Connector;
    private final Aes256Util aes256Util;

    public void validatePageName(PageNameRequest request) {
        if (memberRepository.existsByPageName(request.getPageName())) {
            throw new DuplicatedPageNameException();
        }
    }

    public void validateNickname(NicknameRequest request) {
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new DuplicatedNicknameException();
        }
    }

    public MemberResponse findMemberByPageName(String pageName) {
        Member member = memberRepository.findByPageName(pageName)
                .orElseThrow(MemberNotFoundException::new);
        return new MemberResponse(member);
    }

    public MemberResponse findMemberById(Long id) {
        Member member = findMember(id);
        return new MemberResponse(member);
    }

    public PointResponse findMemberPoint(Long id) {
        Member member = findMember(id);
        return new PointResponse(donationRepository.waitingTotalPoint(member.getId()));
    }

    public List<CurationsResponse> findCurations() {
        return memberRepository.findCurations();
    }

    public ProfileResponse uploadProfile(MultipartFile multipartFile, LoginMember loginMember) {
        Member findMember = findMember(loginMember.getId());
        deleteProfile(findMember);
        String uploadedFile = s3Connector.uploadProfile(multipartFile, loginMember.getId());
        findMember.uploadProfileImage(uploadedFile);
        return new ProfileResponse(uploadedFile);
    }

    public void deleteProfile(LoginMember loginMember) {
        Member findMember = findMember(loginMember.getId());
        deleteProfile(findMember);
    }

    public void updateBio(LoginMember loginMember, String bio) {
        Member member = findMember(loginMember.getId());
        member.updateBio(bio);
    }

    public void updateNickname(LoginMember loginMember, String nickname) {
        Member member = findMember(loginMember.getId());
        member.updateNickname(nickname);
    }

    private void deleteProfile(Member member) {
        if (member.getProfileImage() == null) {
            return;
        }

        s3Connector.delete(member.getProfileImage());
        member.deleteProfile();
    }

    // todo 메서드 이름 변경. 한눈에 알아보거나, 어디서 쓰이는지 유추하기 힘듬. 정산관련 포인트 합계? 그런 의미를 담고 있으면 좋을 듯.
    public DetailedPointResponse detailedPoint(Long id) {
        Long waitingTotalPoint = donationRepository.waitingTotalPoint(id);
        Long exchangedTotalPoint = donationRepository.exchangedTotalPoint(id);
        return new DetailedPointResponse(waitingTotalPoint, exchangedTotalPoint);
    }

    public void registerAccount(LoginMember loginMember, AccountRegisterRequest accountRegisterRequest) {
        Member member = findMember(loginMember.getId());

        String uploadedBankBookUrl = s3Connector.uploadBankBook(accountRegisterRequest.getBankbookImage(),
                loginMember.getId());

        String encryptedAccountNumber = aes256Util.encrypt(accountRegisterRequest.getAccountNumber());
        member.registerAccount(accountRegisterRequest.getAccountHolder(),
                encryptedAccountNumber, accountRegisterRequest.getBank(), uploadedBankBookUrl);
    }

    public AccountInfoResponse accountInfo(LoginMember loginMember) {
        Member member = findMember(loginMember.getId());
        Account account = member.getAccount();

        if (account.isAccountNumberNotEmpty()) {
            String decryptedAccountNumber = aes256Util.decrypt(account.getAccountNumber());

            return new AccountInfoResponse(account.getStatus().name(), account.getAccountHolder(),
                    account.getBank(), decryptedAccountNumber);
        }

        return AccountInfoResponse.of(account);
    }

    public void exchange(Long id) {
        Member member = findMember(id);
        validateRegisteredAccount(member);
        Long waitingTotalPoint = donationRepository.waitingTotalPoint(id);
        validateExchangeable(member, waitingTotalPoint);

        Exchange exchange = new Exchange(member);
        exchangeRepository.save(exchange);
    }

    private void validateRegisteredAccount(Member member) {
        if (member.isAccountNotRegistered()) {
            throw new NotRegisteredAccountException();
        }
    }

    private void validateExchangeable(Member member, Long exchangeablePoint) {
        if (exchangeRepository.existsByStatusAndMember(ExchangeStatus.WAITING, member)) {
            throw new AlreadyRequestExchangeException();
        }
        if (exchangeablePoint < 10000) {
            throw new ExchangeAmountException();
        }
    }

    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }
}
