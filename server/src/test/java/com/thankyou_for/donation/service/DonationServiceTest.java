package com.thankyou_for.donation.service;

import com.thankyou_for.auth.domain.Oauth2Type;
import com.thankyou_for.common.util.SmtpMailConnector;
import com.thankyou_for.donation.domain.Donation;
import com.thankyou_for.donation.domain.Message;
import com.thankyou_for.donation.dto.DonationMessageRequest;
import com.thankyou_for.donation.dto.DonationRequest;
import com.thankyou_for.donation.dto.DonationResponse;
import com.thankyou_for.donation.repository.DonationRepository;
import com.thankyou_for.member.domain.Member;
import com.thankyou_for.member.domain.Point;
import com.thankyou_for.member.exception.WrongDonationOwnerException;
import com.thankyou_for.member.repository.MemberRepository;
import com.thankyou_for.payment.domain.Payment;
import com.thankyou_for.payment.repository.PaymentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import com.thankyou_for.supports.IntegrationTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;

@IntegrationTest
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
        DonationRequest donationRequest = donationRequest(creator, 1000L);

        //when
        assertThat(donator.getGivenDonations()).hasSize(0);
        assertThat(creator.getReceivedDonations()).hasSize(0);
        DonationResponse donationResponse = createDonation(donationRequest, donator);

        //then
        assertThat(donationResponse.getDonationId()).isNotNull();
        em.flush();
        em.clear();

        Member findDonator = memberRepository.findById(donator.getId()).get();
        Member findCreator = memberRepository.findById(creator.getId()).get();

        assertThat(findDonator.getPoint()).isEqualTo(4000L);
        assertThat(findDonator.getGivenDonations()).hasSize(1);

        assertThat(findCreator.getReceivedDonations()).hasSize(1);
    }

    @Test
    @DisplayName("addMessageToDonation")
    public void addMessageToDonationTest() {
        //given
        DonationRequest donationRequest = donationRequest(creator, 1000L);
        DonationResponse donationResponse = createDonation(donationRequest, donator);
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
    @DisplayName("addMessageToDonation - Wrong Owner Case")
    public void addMessageToDonationNotOwnerCase() {
        //given
        DonationRequest donationRequest = donationRequest(creator, 1000L);
        DonationResponse donationResponse = createDonation(donationRequest, donator);
        DonationMessageRequest donationMessageRequest = new DonationMessageRequest("message", false);

        // when & then
        assertThatThrownBy(() -> {
            donationService.addMessageToDonation(creator.getId(), donationResponse.getDonationId(), donationMessageRequest);
        }).isInstanceOf(WrongDonationOwnerException.class);
    }

    @Test
    @DisplayName("findMyDonations")
    public void findMyDonationsTest() {
        //given
        List<DonationResponse> donationsBefore = donationService.findMyDonations(creator.getId(), PageRequest.of(0, 1));
        assertThat(donationsBefore).hasSize(0);
        DonationRequest donationRequest = donationRequest(creator, 1000L);
        donationService.createDonation(donationRequest, donator.getId());

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

        DonationRequest donationRequest = donationRequest(creator, 1000L);

        DonationResponse donationResponse = createDonation(donationRequest, donator);
        DonationMessageRequest secretMessageRequest = new DonationMessageRequest("secretMessage", true);
        donationService.addMessageToDonation(donator.getId(), donationResponse.getDonationId(), secretMessageRequest);

        DonationResponse donationResponse2 = createDonation(donationRequest, donator);
        DonationMessageRequest nonSecretMessageRequest = new DonationMessageRequest("nonSecretMessage", false);
        donationService.addMessageToDonation(donator.getId(), donationResponse2.getDonationId(), nonSecretMessageRequest);

        //when
        List<DonationResponse> publicDonationsAfter = donationService.findPublicDonations(creator.getPageName());

        //then
        assertThat(publicDonationsAfter).hasSize(2);

        assertThat(publicDonationsAfter.get(1).getMessage()).isEqualTo(Message.SECRET_MESSAGE);
        assertThat(publicDonationsAfter.get(1).getName()).isEqualTo(Message.SECRET_NAME);
        assertThat(publicDonationsAfter.get(1).getPageName()).isEqualTo(Message.SECRET_PAGE_NAME);

        assertThat(publicDonationsAfter.get(0).getMessage()).isEqualTo(nonSecretMessageRequest.getMessage());
        assertThat(publicDonationsAfter.get(0).getName()).isEqualTo(donator.getNickname());
        assertThat(publicDonationsAfter.get(0).getPageName()).isEqualTo(donator.getPageName());
    }

    private DonationRequest donationRequest(Member member, Long point) {
        return new DonationRequest(member.getPageName(), point);
    }

    private DonationResponse createDonation(DonationRequest request, Member member) {
        return donationService.createDonation(request, member.getId());
    }
}
