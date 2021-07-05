package com.example.tyfserver.donation.service;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DonationServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    DonationRepository donationRepository;

    @Autowired
    DonationService donationService;

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member("tyf@gmail.com");
        memberRepository.save(member);
    }

    @DisplayName("후원을 등록한다.")
    @Test
    void createDonation() throws Exception {
        //given
        DonationRequest donationRequest = new DonationRequest(member.id(), 1000L);

        //when
        DonationResponse donationResponse = donationService.createDonation(donationRequest);
        //then
        Assertions.assertEquals(member.id(), donationResponse.getDonationId());
    }

    @DisplayName("후원받은 포인트만큼 멤버의 포인트에 누적된다.")
    @Test
    void accumulateMemberPoint() throws Exception {
        //given
        DonationRequest donationRequest = new DonationRequest(member.id(), 1000L);

        //when
        donationService.createDonation(donationRequest);
        //then
        Member findMember = memberRepository.findById(this.member.id()).get();
        Assertions.assertTrue(findMember.isSamePoint(1000L));
    }


    @DisplayName("후원 메시지를 추가한다.")
    @Test
    void addDonationMessage() throws Exception {
        //given
        Donation donation = new Donation(1000L);
        final Donation savedDonation = donationRepository.save(donation);
        DonationMessageRequest donationMessageRequest = new DonationMessageRequest("test", "this is test message");

        //when
        donationService.addMessageToDonation(savedDonation.id(), donationMessageRequest);

        //then
        final Donation findDonation = donationRepository.findById(savedDonation.id()).get();
        Assertions.assertEquals(donationMessageRequest.getName(), findDonation.name());
        Assertions.assertEquals(donationMessageRequest.getMessage(), findDonation.message());

    }
}