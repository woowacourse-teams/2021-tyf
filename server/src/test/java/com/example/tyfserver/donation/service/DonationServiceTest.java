package com.example.tyfserver.donation.service;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.Point;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.repository.PaymentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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

    @Autowired
    private EntityManager em;

    @MockBean
    private SmtpMailConnector mailConnector;

    private final UUID uuid = UUID.randomUUID();
    private final Member donator = new Member("donator@gmail.com", "donator", "donatorPageName", Oauth2Type.GOOGLE, "profile", new Point(5000L));
    private final Member creator = new Member("creator@gmail.com", "creator", "creatorPageName", Oauth2Type.GOOGLE, "profile", new Point(0L));
    private final Payment payment = new Payment(1000L, "email", "pagename", uuid);

    @BeforeEach
    void setUp() {
        paymentRepository.save(payment);
        memberRepository.save(donator);
        memberRepository.save(creator);
        doNothing().when(mailConnector).sendChargeComplete(any(Payment.class));
    }

    @AfterEach
    void tearDown() {
        paymentRepository.delete(payment);
        memberRepository.delete(donator);
        memberRepository.delete(creator);
    }

    @Test
    @DisplayName("createDonation")
    public void createDonationTest() {
        //given
        DonationRequest request = new DonationRequest(creator.getPageName(), 1000L);

        //when
        assertThat(donator.getGivingDonations()).hasSize(0);
        assertThat(creator.getGivenDonations()).hasSize(0);
        DonationResponse response = donationService.createDonation(request, donator.getId());

        //then
        assertThat(response.getDonationId()).isNotNull();
        em.flush();
        em.clear();

        Member findDonator = memberRepository.findById(donator.getId()).get();
        Member findCreator = memberRepository.findById(creator.getId()).get();

        assertThat(findDonator.getPoint()).isEqualTo(4000L);
        assertThat(findDonator.getGivingDonations()).hasSize(1);

        assertThat(findCreator.getPoint()).isEqualTo(request.getPoint());
        assertThat(findCreator.getGivenDonations()).hasSize(1);
    }

    @Test
    @DisplayName("addMessageToDonation")
    public void addMessageToDonationTest() {
        //given
        DonationRequest request = new DonationRequest(creator.getPageName(), 1000L);
        DonationResponse donationResponse = donationService.createDonation(request, donator.getId());
        DonationMessageRequest donationMessageRequest = new DonationMessageRequest("message", false);
        assertThat(donationResponse.getMessage()).isEqualTo(Message.DEFAULT_MESSAGE);

        // when
        donationService.addMessageToDonation(donator.getId(), donationResponse.getDonationId(), donationMessageRequest);

        //then
        Donation donation = donationRepository.findById(donationResponse.getDonationId()).get();
        assertThat(donation.getName()).isEqualTo("donator");
        assertThat(donation.getMessage()).isEqualTo("message");
    }

    @Test
    @DisplayName("findMyDonations")
    public void findMyDonationsTest() {
        //given
        List<DonationResponse> donationsBefore = donationService.findMyDonations(creator.getId(), PageRequest.of(0, 1));
        assertThat(donationsBefore).hasSize(0);
        DonationRequest request = new DonationRequest(creator.getPageName(), 1000L);
        donationService.createDonation(request, donator.getId());

        //when
        List<DonationResponse> donationsAfter = donationService.findMyDonations(creator.getId(), PageRequest.of(0, 1));

        //then
        assertThat(donationsAfter).hasSize(1);
    }

    @Test
    @DisplayName("findPublicDonations")
    public void findPublicDonationsTest() {
        //given
        List<DonationResponse> publicDonationsBefore = donationService.findPublicDonations(creator.getPageName());
        assertThat(publicDonationsBefore).hasSize(0);
        DonationRequest donationRequest = new DonationRequest(creator.getPageName(), 1000L);
        DonationResponse donationResponse = donationService.createDonation(donationRequest, donator.getId());

        DonationMessageRequest messageRequest = new DonationMessageRequest("secretMessage", true);
        donationService.addMessageToDonation(donator.getId(), donationResponse.getDonationId(), messageRequest);

        //when
        List<DonationResponse> publicDonationsAfter = donationService.findPublicDonations(creator.getPageName());

        //then
        assertThat(publicDonationsAfter).hasSize(1);
        assertThat(publicDonationsAfter.get(0).getMessage()).isEqualTo(Message.SECRET_MESSAGE);
        assertThat(publicDonationsAfter.get(0).getName()).isEqualTo(Message.SECRET_NAME);
    }
}
