package com.example.tyfserver.donation.service;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.MemberTest;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DonationServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private DonationService donationService;

    private Member member;

    @BeforeEach
    void setUp() {
        member = MemberTest.testMember();
        memberRepository.save(member);
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @DisplayName("후원을 등록한다.")
    @Test
    void createDonation() throws Exception {
        //given
        DonationRequest donationRequest = new DonationRequest(member.getPageName(), 1000L);

        //when
        DonationResponse donationResponse = donationService.createDonation(donationRequest);

        //then
        List<Donation> donations = donationRepository.findAll();
        assertThat(donations).hasSize(1);
        assertThat(donationResponse.getDonationId()).usingRecursiveComparison()
                .isEqualTo(donations.get(0).getId());
    }

    @DisplayName("후원받은 포인트만큼 멤버의 포인트에 누적된다.")
    @Test
    void accumulateMemberPoint() throws Exception {
        //given
        DonationRequest donationRequest = new DonationRequest(member.getPageName(), 1000L);

        //when
        donationService.createDonation(donationRequest);
        //then
        Member findMember = memberRepository.findById(this.member.getId()).get();
        assertThat(1000L).isEqualTo(findMember.getPoint().getPoint());
    }


    @DisplayName("후원 메시지를 추가한다.")
    @Test
    void addDonationMessage() throws Exception {
        //given
        Donation donation = new Donation(1000L);
        final Donation savedDonation = donationRepository.save(donation);
        DonationMessageRequest donationMessageRequest = new DonationMessageRequest("test", "this is test message");

        //when
        donationService.addMessageToDonation(savedDonation.getId(), donationMessageRequest);

        //then
        final Donation findDonation = donationRepository.findById(savedDonation.getId()).get();
        assertThat(donationMessageRequest.getName()).isEqualTo(findDonation.getName());
        assertThat(donationMessageRequest.getMessage()).isEqualTo(findDonation.getMessage());
    }
}