package com.example.tyfserver.donation.service;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Member member = memberRepository.findById(donationRequest.getCreatorId())
                .orElseThrow(() -> new RuntimeException("회원이 없습니다"));
        Donation donation = new Donation(donationRequest.getDonationAmount());
        donationRepository.save(donation);
        member.addDonation(donation);
        member.addPoint(donationRequest.getDonationAmount());
        return new DonationResponse(donation);
    }

    public void addMessageToDonation(final Long donationId, final DonationMessageRequest donationMessageRequest) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("후원 데이터가 존재하지 않습니다"));

        donation.addMessage(
                donationMessageRequest.getName(), donationMessageRequest.getMessage(), donationMessageRequest.isSecret());
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
            donation.hideNameAndMessage();
        }
    }

    private List<DonationResponse> convertDonationsToResponses(List<Donation> donations) {
        return donations.stream()
                .map(DonationResponse::new)
                .collect(Collectors.toList());
    }
}
