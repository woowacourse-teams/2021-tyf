package com.example.tyfserver.donation.service;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.exception.DonationNotFoundException;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationService {

    private final DonationRepository donationRepository;
    private final MemberRepository memberRepository;

    public DonationResponse createDonation(DonationRequest donationRequest, long donatorId) {
        Member donator = findMember(donatorId);
        Member creator = memberRepository.findByPageName(donationRequest.getPageName())
                .orElseThrow(MemberNotFoundException::new);

        donator.validateEnoughPoint(donationRequest.getPoint());

        Message message = new Message(donator.getNickname());
        Donation savedDonation = donationRepository.save(new Donation(message, donationRequest.getPoint()));
        savedDonation.donate(donator, creator);

        return new DonationResponse(savedDonation);
    }

    public void addMessageToDonation(final Long requestMemberId,
                                     final Long donationId, final DonationMessageRequest donationMessageRequest) {
        Member requestMember = findMember(requestMemberId);
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(DonationNotFoundException::new);
        requestMember.validateMemberGivenDonation(donation);

        donation.addMessage(donationMessageRequest.toEntity(requestMember.getNickname()));
    }

    @Transactional(readOnly = true)
    public List<DonationResponse> findMyDonations(Long memberId, Long lastPageId) {
        Member findMember = findMember(memberId);

        return privateDonationResponses(
                donationRepository.find5NewestDonationsPage(findMember, lastPageId)
        );
    }

    @Transactional(readOnly = true)
    public List<DonationResponse> findPublicDonations(String pageName) {
        Member findMember = memberRepository.findByPageName(pageName)
                .orElseThrow(MemberNotFoundException::new);

        return publicDonationResponses(
                donationRepository.find5NewestDonationsPage(findMember, 0L)
        );
    }

    private List<DonationResponse> privateDonationResponses(List<Donation> donations) {
        return donations.stream()
                .map(DonationResponse::new)
                .collect(Collectors.toList());
    }

    private List<DonationResponse> publicDonationResponses(List<Donation> donations) {
        return donations.stream()
                .map(DonationResponse::forPublic)
                .collect(Collectors.toList());
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
