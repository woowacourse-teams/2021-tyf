package com.example.tyfserver.donation.service;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationService {

    private final DonationRepository donationRepository;
    private final MemberRepository memberRepository;

    public DonationResponse createDonation(final DonationRequest donationRequest) {
        Member member = memberRepository.findById(donationRequest.getCreatorId())
                .orElseThrow(() -> new RuntimeException("error_donation_request"));

        Donation donation = new Donation(donationRequest.getDonationAmount());
        member.addDonation(donation);
        member.addPoint(donationRequest.getDonationAmount());
        return DonationResponse.from(member);
    }

    public void addMessageToDonation(final Long donationId, final DonationMessageRequest donationMessageRequest) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("error_donation_message_send"));

        donation.addMessage(
                donationMessageRequest.getName(), donationMessageRequest.getMessage(), donationMessageRequest.isSecret());
    }
}
