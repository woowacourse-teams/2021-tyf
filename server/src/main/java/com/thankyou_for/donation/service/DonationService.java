package com.thankyou_for.donation.service;

import com.thankyou_for.donation.domain.Donation;
import com.thankyou_for.donation.domain.Message;
import com.thankyou_for.donation.dto.DonationMessageRequest;
import com.thankyou_for.donation.dto.DonationRequest;
import com.thankyou_for.donation.dto.DonationResponse;
import com.thankyou_for.donation.exception.DonationNotFoundException;
import com.thankyou_for.donation.repository.DonationRepository;
import com.thankyou_for.member.domain.Member;
import com.thankyou_for.member.exception.MemberNotFoundException;
import com.thankyou_for.member.repository.MemberRepository;
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
    public List<DonationResponse> findMyDonations(Long memberId, Pageable pageable) {
        Member findMember = findMember(memberId);

        return privateDonationResponses(
                donationRepository.findDonationByCreatorOrderByCreatedAtDesc(findMember, pageable)
        );
    }

    @Transactional(readOnly = true)
    public List<DonationResponse> findPublicDonations(String pageName) {
        Member findMember = memberRepository.findByPageName(pageName)
                .orElseThrow(MemberNotFoundException::new);

        return publicDonationResponses(
                donationRepository.findDonationByCreatorOrderByCreatedAtDesc(findMember, PageRequest.of(0, 5))
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
