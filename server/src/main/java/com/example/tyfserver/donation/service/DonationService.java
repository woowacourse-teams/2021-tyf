package com.example.tyfserver.donation.service;

import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.exception.DonationNotFoundException;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.exception.MemberNotFoundException;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.dto.PaymentCompleteRequest;
import com.example.tyfserver.payment.service.PaymentService;
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
    private final PaymentService paymentService;
    private final SmtpMailConnector mailConnector;

    public DonationResponse createDonation(PaymentCompleteRequest paymentCompleteRequest) {
        Payment payment = paymentService.completePayment(paymentCompleteRequest);
        Donation donation = new Donation(payment);
        Member member = memberRepository.findByPageName(payment.getPageName())
                .orElseThrow(MemberNotFoundException::new);

        Donation savedDonation = donationRepository.save(donation);
        member.addDonation(savedDonation);

        mailConnector.sendDonationComplete(payment.getEmail(), payment.getMerchantUid());
        return new DonationResponse(savedDonation);
    }

    public void addMessageToDonation(final Long donationId, final DonationMessageRequest donationMessageRequest) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(DonationNotFoundException::new);

        donation.addMessage(donationMessageRequest.toEntity());
    }

    public List<DonationResponse> findMyDonations(Long memberId, Pageable pageable) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        List<Donation> donations = donationRepository.findDonationByMemberOrderByCreatedAtDesc(findMember, pageable);

        return privateDonationResponses(donations);
    }

    public List<DonationResponse> findPublicDonations(String pageName) {
        Member findMember = memberRepository.findByPageName(pageName)
                .orElseThrow(MemberNotFoundException::new);

        List<Donation> donations = donationRepository.findFirst5ByMemberOrderByCreatedAtDesc(findMember);

        return publicDonationResponses(donations);
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
}
