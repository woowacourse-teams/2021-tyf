package com.example.tyfserver.donation.service;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.donation.domain.MessageTest;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.Point;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.repository.PaymentRepository;
import com.example.tyfserver.payment.service.PaymentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@Transactional
class DonationServiceTest {

    @Autowired
    private DonationService donationService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private DonationRepository donationRepository;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private SmtpMailConnector mailConnector;

    private final UUID uuid = UUID.randomUUID();
    private final Member member = new Member("email", "nickname", "pagename", Oauth2Type.GOOGLE, "profile", new Point(5000L));
    private final Payment payment = new Payment(1000L, "email", "pagename", uuid);
    private final Donation donation = new Donation(MessageTest.testMessage(), 1000L);

    @BeforeEach
    void setUp() {
        paymentRepository.save(payment);
        memberRepository.save(member);
        donationRepository.save(donation);
        doNothing().when(mailConnector).sendChargeComplete(any(Payment.class));
    }

    @AfterEach
    void tearDown() {
        paymentRepository.delete(payment);
        memberRepository.delete(member);
        donationRepository.delete(donation);
    }

    @Test
    @DisplayName("createDonation")
    public void createDonationTest() {
        //given
        Member creator = new Member("creator", "creator", "creator",
                Oauth2Type.GOOGLE, "profile", new Point(5000L));
        memberRepository.save(creator);

        DonationRequest request = new DonationRequest(creator.getPageName(), 1000L);
        Member donator = memberRepository.findById(member.getId()).get();

        //when
        assertThat(donator.getGivingDonations()).hasSize(0);
        DonationResponse response = donationService.createDonation(request, donator.getId());

        //then
        assertThat(response.getDonationId()).isNotNull();

        Member member = memberRepository.findById(donator.getId()).get();
        assertThat(member.getPoint()).isEqualTo(5000L - request.getPoint());

        Member saveCreator = memberRepository.findById(creator.getId()).get();
        assertThat(saveCreator.getGivenDonations()).hasSize(1);
        assertThat(donationRepository.currentPoint(saveCreator.getId())).isEqualTo(request.getPoint());
        assertThat(donator.getGivingDonations()).hasSize(1);
    }

    @Test
    @DisplayName("addMessageToDonation")
    public void addMessageToDonationTest() {
        //given
        DonationMessageRequest request = new DonationMessageRequest("name", "message", false);

        // when
        donationService.addMessageToDonation(donation.getId(), request);

        //then
        Donation donation = donationRepository.findById(this.donation.getId()).get();
        assertThat(donation.getName()).isEqualTo("name");
        assertThat(donation.getMessage()).isEqualTo("message");
    }

    @Test
    @DisplayName("findMyDonations")
    public void findMyDonationsTest() {
        //given
        List<DonationResponse> donationsBefore = donationService.findMyDonations(member.getId(), PageRequest.of(0, 1));
        assertThat(donationsBefore).hasSize(0);
        DonationRequest request = new DonationRequest(member.getPageName(), 1000L);
        donationService.createDonation(request, member.getId());

        //when
        List<DonationResponse> donationsAfter = donationService.findMyDonations(member.getId(), PageRequest.of(0, 1));

        //then
        assertThat(donationsAfter).hasSize(1);
    }

    @Test
    @DisplayName("findPublicDonations")
    public void findPublicDonationsTest() {
        //given
        List<DonationResponse> publicDonationsBefore = donationService.findPublicDonations(member.getPageName());
        assertThat(publicDonationsBefore).hasSize(0);

        DonationRequest donationRequest = new DonationRequest(member.getPageName(), 1000L);
        DonationResponse donationResponse = donationService.createDonation(donationRequest, member.getId());
        DonationMessageRequest messageRequest = new DonationMessageRequest("test", Message.DEFAULT_MESSAGE);
        donationService.addMessageToDonation(donationResponse.getDonationId(), messageRequest);

        //when
        List<DonationResponse> publicDonationsAfter = donationService.findPublicDonations(member.getPageName());

        //then
        assertThat(publicDonationsAfter).hasSize(1);
    }
}
