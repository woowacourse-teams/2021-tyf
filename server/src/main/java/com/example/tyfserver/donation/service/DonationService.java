package com.example.tyfserver.donation.service;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.exception.DonationNotFoundException;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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

    public DonationResponse createDonation(final DonationRequest donationRequest) {
        Member member = memberRepository.findByPageName(donationRequest.getPageName())
                .orElseThrow(MemberNotFoundException::new);
        Donation donation = new Donation(donationRequest.getDonationAmount());
        donationRepository.save(donation);
        member.addDonation(donation);
        return new DonationResponse(donation);
    }

    public void addMessageToDonation(final Long donationId, final DonationMessageRequest donationMessageRequest) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(DonationNotFoundException::new);

        donation.addMessage(donationMessageRequest.toEntity());
    }

    public List<DonationResponse> findMyDonations(Long memberId, Pageable pageable) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));

        List<Donation> donations = donationRepository.findDonationByMemberOrderByCreatedAtDesc(findMember, pageable);

        return convertDonationsToResponses(donations);
    }

    public List<DonationResponse> findPublicDonations(String pageName) {
        Member findMember = memberRepository.findByPageName(pageName)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));

        List<Donation> donations = donationRepository.findFirst5ByMemberOrderByCreatedAtDesc(findMember);
        hideSecretDonation(donations);

        return convertDonationsToResponses(donations);
    }

    private void hideSecretDonation(List<Donation> donations) {
        for (Donation donation : donations) {
            if (donation.isSecret()) {
                donation.hideNameAndMessage();
            }
        }
    }

    private List<DonationResponse> convertDonationsToResponses(List<Donation> donations) {
        return donations.stream()
                .map(DonationResponse::new)
                .collect(Collectors.toList());
    }
}
