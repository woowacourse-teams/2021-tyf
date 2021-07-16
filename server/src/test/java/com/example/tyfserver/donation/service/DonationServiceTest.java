package com.example.tyfserver.donation.service;

import com.example.tyfserver.donation.DonationTest;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.MemberTest;
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
        DonationRequest donationRequest = DonationTest.testDonationRequest(member);

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
        DonationRequest donationRequest = DonationTest.testDonationRequest(member);

        //when
        donationService.createDonation(donationRequest);

        //then
        Member findMember = memberRepository.findById(this.member.getId()).get();
        assertThat(findMember.getPoint())
                .isEqualTo(donationRequest.getDonationAmount());
    }

    @DisplayName("후원 메시지를 추가한다.")
    @Test
    void addDonationMessage() throws Exception {
        //given
        Donation donation = new Donation(1000L);
        final Donation savedDonation = donationRepository.save(donation);
        DonationMessageRequest donationMessageRequest = DonationTest.testMessageRequest();

        //when
        donationService.addMessageToDonation(savedDonation.getId(), donationMessageRequest);

        //then
        final Donation findDonation = donationRepository.findById(savedDonation.getId()).get();
        assertThat(donationMessageRequest.getName()).isEqualTo(findDonation.getName());
        assertThat(donationMessageRequest.getMessage()).isEqualTo(findDonation.getMessage());
    }

    @DisplayName("창작자의 후원메시지 목록을 조회한다. 비공개 메시지는 익명으로 처리된다.")
    @Test
    void findPublicDonations() {
        // given
        DonationResponse donationResponse1 = 후원하기();
        DonationResponse donationResponse2 = 후원하기();

        후원메시지_보내기(donationResponse1, DonationTest.testMessageRequest());
        후원메시지_보내기(donationResponse2, DonationTest.testSecretMessageRequest());

        // when
        List<DonationResponse> responses = donationService.findPublicDonations(member.getPageName());

        // then
        assertThat(responses).hasSize(2);

        DonationResponse secretResponse = responses.get(0);
        DonationResponse publicResponse = responses.get(1);

        assertThat(secretResponse.getName()).isEqualTo(Message.SECRET_NAME);
        assertThat(secretResponse.getMessage()).isEqualTo(Message.SECRET_MESSAGE);
        assertThat(secretResponse.getAmount()).isEqualTo(DonationTest.DONATION_AMOUNT);

        assertThat(publicResponse.getName()).isEqualTo(DonationTest.NAME);
        assertThat(publicResponse.getMessage()).isEqualTo(DonationTest.MESSAGE);
        assertThat(publicResponse.getAmount()).isEqualTo(DonationTest.DONATION_AMOUNT);
    }

    private DonationResponse 후원하기() {
        DonationRequest request = DonationTest.testDonationRequest(member);
        return donationService.createDonation(request);
    }

    private void 후원메시지_보내기(DonationResponse donationResponse1, DonationMessageRequest messageRequest) {
        donationService.addMessageToDonation(donationResponse1.getDonationId(), messageRequest);
    }
}