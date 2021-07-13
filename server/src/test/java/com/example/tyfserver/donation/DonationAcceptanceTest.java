package com.example.tyfserver.donation;

import com.example.tyfserver.AcceptanceTest;
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
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class DonationAcceptanceTest extends AcceptanceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DonationRepository donationRepository;

    private Member member;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        member = memberRepository.save(MemberTest.testMember());
    }

    @AfterEach
    void tearDown() {
        donationRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("후원자가 창작자에게 후원한다.")
    @Test
    void 창작자에게_후원한다() {
        Long donationId = 후원을_생성한다();

        DonationMessageRequest messageRequest = new DonationMessageRequest(
                "joy", "응원합니다", true);
        후원메시지를_생성한다(donationId, messageRequest);

        Donation donation = donationRepository.findById(donationId).get();
        assertThat(donation.getMessage()).isEqualTo(messageRequest.getMessage());
        assertThat(donation.getName()).isEqualTo(messageRequest.getName());
        assertThat(donation.isSecret()).isEqualTo(messageRequest.isSecret());
    }

    private Long 후원을_생성한다() {
        // given
        long memberId = member.getId();
        long amount = 10000L;
        DonationRequest donationRequest = new DonationRequest(memberId, amount);

        // when // then
        Long donationId = post("/donations", donationRequest)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(DonationResponse.class).getDonationId();

        assertThat(donationId).isEqualTo(memberId);
        return donationId;
    }

    private void 후원메시지를_생성한다(Long donationId, DonationMessageRequest messageRequest) {
        // given
        String url = "/donations/" + donationId + "/messages";

        // when // then
        post(url, messageRequest)
                .statusCode(HttpStatus.CREATED.value());
    }
}
