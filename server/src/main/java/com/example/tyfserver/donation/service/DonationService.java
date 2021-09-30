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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Donation creatorDonation = new Donation(message, donationRequest.getPoint());
        Donation savedDonation = donationRepository.save(creatorDonation);
        donator.reducePoint(donationRequest.getPoint());
        creator.addGivenDonation(savedDonation);
        donator.addGivingDonation(savedDonation);

        return new DonationResponse(savedDonation);
    }

    public void addMessageToDonation(final Long requestMemberId,
                                     final Long donationId, final DonationMessageRequest donationMessageRequest) {
        Member requestMember = findMember(requestMemberId);
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(DonationNotFoundException::new);
        requestMember.validateMemberGivingDonation(donation);

        donation.addMessage(donationMessageRequest.toEntity(requestMember.getNickname()));
    }

    public List<DonationResponse> findMyDonations(Long memberId, Pageable pageable) {
        Member findMember = findMember(memberId);

        return privateDonationResponses(
                donationRepository.findDonationByCreator(findMember, pageable)
        );
    }

    public List<DonationResponse> findPublicDonations(String pageName) {
        Member findMember = memberRepository.findByPageName(pageName)
                .orElseThrow(MemberNotFoundException::new);

        return publicDonationResponses(
                donationRepository.findDonationByCreator(findMember, PageRequest.of(0, 5))
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
