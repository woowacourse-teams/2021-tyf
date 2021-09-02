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
import com.example.tyfserver.payment.dto.PaymentCompleteRequest;
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
    @DisplayName("성공적인 후원 생성의 경우")
    public void createDonation() {
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", "pagename");
        PaymentPendingResponse pendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", "pagename").as(PaymentPendingResponse.class);

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
    @DisplayName("존재하지 않는 페이먼트에 대한 후원 생성의 경우")
    public void createDonationCreatorNotFound() {
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", "tyfpagename");
        페이먼트_생성(10000L, "donator@gmail.com", "tyfpagename");
        ErrorResponse errorResponse = 후원_생성("impUid", UUID.randomUUID().toString()).as(ErrorResponse.class);

        String token = jwtTokenProvider.createToken(member.getId(), member.getEmail());

    @Test
    @DisplayName("성공적인 후원 메세지 전송")
    public void addDonationMessage() {
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", "pagename");
        PaymentPendingResponse pendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", "pagename").as(PaymentPendingResponse.class);
        Long donationId = 후원_생성("impUid", pendingResponse.getMerchantUid().toString()).as(DonationResponse.class).getDonationId();

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

    @Test
    @DisplayName("존재하지 않은 후원에 대한 후원 메세지 전송")
    public void addDonationMessageDonationNotFound() {
        ErrorResponse errorResponse = 후원_메세지_생성(10000L, "bepoz", "positive", false).as(ErrorResponse.class);

        assertThat(errorResponse.getErrorCode()).isEqualTo(DonationNotFoundException.ERROR_CODE);
    }

    @Test
    @DisplayName("나의 후원목록 조회")
    public void totalDonations() {
        SignUpResponse signUpResponse = 회원가입_후_로그인되어_있음("creator@gmail.com", "KAKAO", "nickname", "pagename");
        PaymentPendingResponse pendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", "pagename").as(PaymentPendingResponse.class);
        Long donationId = 후원_생성("impUid", pendingResponse.getMerchantUid().toString()).as(DonationResponse.class).getDonationId();
        후원_메세지_생성(donationId, "donator", "thisismessage", true);

        // when // then
        return post("/donations", request)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(DonationResponse.class).getDonationId();
    }

    @Test
    @DisplayName("공개 후원목록 조회")
    public void publicDonations() {
        회원생성을_요청("creator@gmail.com", "KAKAO", "nickname", "pagename");
        PaymentPendingResponse pendingResponse = 페이먼트_생성(10000L, "donator@gmail.com", "pagename").as(PaymentPendingResponse.class);
        Long donationId = 후원_생성("impUid", pendingResponse.getMerchantUid().toString()).as(DonationResponse.class).getDonationId();
        후원_메세지_생성(donationId, "donator", "thisismessage", true);

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
