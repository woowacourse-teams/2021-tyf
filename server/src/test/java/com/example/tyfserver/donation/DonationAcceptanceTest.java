package com.example.tyfserver.donation;

import com.example.tyfserver.AcceptanceTest;
import com.example.tyfserver.auth.util.JwtTokenProvider;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.DonationTest;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.dto.PaymentRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class DonationAcceptanceTest extends AcceptanceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private Member member;
    private Member member2;


    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        member = memberRepository.save(MemberTest.testMember());
        member2 = memberRepository.save(MemberTest.testMember2());
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

        DonationMessageRequest messageRequest = DonationTest.testMessageRequest();
        후원메시지를_보낸다(donationId, messageRequest);

        후원메시지_검증(donationId, messageRequest);
    }

    @Test
    @DisplayName("공개된 후원 목록을 조회한다.")
    public void 공개_후원_리스트() {
        //given
        Long donationId = 후원을_생성한다();

        DonationMessageRequest messageRequest = DonationTest.testMessageRequest();
        후원메시지를_보낸다(donationId, messageRequest);

        //when //then
        get("/donations/public/pageName")
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getList(".", DonationResponse.class);
    }

    @Test
    @DisplayName("창작자가 자신이 받은 후원 목록을 조회한다.")
    public void 전체_후원_리스트() {
        //given
        Long donationId = 후원을_생성한다();
        후원메시지를_보낸다(donationId, DonationTest.testMessageRequest());
        Long donationId2 = 후원을_생성한다(member2);
        후원메시지를_보낸다(donationId2, new DonationMessageRequest("poz", "화이팅"));

        String token = jwtTokenProvider.createToken(member.getId(), member.getEmail());

        //when
        List<DonationResponse> donations = authGet("/donations/me", token)
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().getList(".", DonationResponse.class);

        //then
        assertThat(donations).hasSize(1);

        assertThat(donations.get(0).getName()).isEqualTo(DonationTest.NAME);
        assertThat(donations.get(0).getMessage()).isEqualTo(DonationTest.MESSAGE);
        assertThat(donations.get(0).getAmount()).isEqualTo(DonationTest.DONATION_AMOUNT);
    }

    @Test
    @DisplayName("창작자가 자신이 받은 후원 목록을 조회한다.")
    public void 전체_후원_리스트_실패() {
        //given
        Long donationId = 후원을_생성한다();
        DonationMessageRequest messageRequest = DonationTest.testMessageRequest();
        후원메시지를_보낸다(donationId, messageRequest);
        String token = jwtTokenProvider.createToken(member.getId(), member.getEmail());

        //when //then
        authGet("/donations/me", token)
                .statusCode(HttpStatus.OK.value())
                .extract().body()
                .jsonPath().getList(".", DonationResponse.class);
    }

    private Long 후원을_생성한다() {
        return 후원을_생성한다(member);
    }

    private Long 후원을_생성한다(Member member) {
        // given
        PaymentRequest paymentRequest = new PaymentRequest(member.getPageName(), UUID.randomUUID());

        // when // then
        return post("/donations", paymentRequest)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(DonationResponse.class).getDonationId();
    }

    private void 후원메시지를_보낸다(Long donationId, DonationMessageRequest messageRequest) {
        // given
        String url = String.format("/donations/%d/messages", donationId);

        // when // then
        post(url, messageRequest)
                .statusCode(HttpStatus.CREATED.value());
    }

    private void 후원메시지_검증(Long donationId, DonationMessageRequest messageRequest) {
        Donation donation = donationRepository.findById(donationId).get();
        assertThat(donation.getMessage()).isEqualTo(messageRequest.getMessage());
        assertThat(donation.getName()).isEqualTo(messageRequest.getName());
        assertThat(donation.isSecret()).isEqualTo(messageRequest.isSecret());
    }
}
