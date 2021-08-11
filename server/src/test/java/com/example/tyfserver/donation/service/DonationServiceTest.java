package com.example.tyfserver.donation.service;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.common.util.SmtpMailConnector;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.dto.PaymentCompleteRequest;
import com.example.tyfserver.payment.repository.PaymentRepository;
import com.example.tyfserver.payment.service.PaymentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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

    private UUID uuid = UUID.randomUUID();
    private Member member = new Member("email", "nickname", "pagename", Oauth2Type.GOOGLE, "profile");
    private Payment payment = new Payment(1000L, "email", "pagename", uuid);
    private Donation donation = new Donation(payment, new Message(Message.DEFAULT_NAME, Message.DEFAULT_MESSAGE, true));

    @BeforeEach
    void setUp() {
        paymentRepository.save(payment);
        memberRepository.save(member);
        donationRepository.save(donation);
        doNothing().when(mailConnector).sendDonationComplete(anyString(), any());
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
        PaymentCompleteRequest request = new PaymentCompleteRequest("impUid", "merchantUid");

        //when
        when(paymentService.completePayment(request))
                .thenReturn(new Payment(payment.getId(), 1000L, "email", "pagename", uuid));

        //then
        DonationResponse response = donationService.createDonation(request);
        assertThat(response.getDonationId()).isNotNull();

        Member member = memberRepository.findByPageName("pagename").get();
        assertThat(member.getDonations()).hasSize(1);
    }

    @Test
    @DisplayName("addMessageToDonation")
    public void addMessageToDonationTest() {
        //given&when
        donationService.addMessageToDonation(donation.getId(), new DonationMessageRequest("name", "message", false));

        //then
        Donation donation = donationRepository.findById(this.donation.getId()).get();
        assertThat(donation.getName()).isEqualTo("name");
        assertThat(donation.getMessage()).isEqualTo("message");
    }

    @Test
    @DisplayName("findMyDonations")
    public void findMyDonationsTest() {
        //given
        List<DonationResponse> myDonations = donationService.findMyDonations(member.getId(), PageRequest.of(0, 1));
        PaymentCompleteRequest paymentCompleteRequest = new PaymentCompleteRequest(payment.getImpUid(), payment.getMerchantUid().toString());

        //when
        when(paymentService.completePayment(paymentCompleteRequest))
                .thenReturn(payment);
        //then
        assertThat(myDonations).hasSize(0);

        donationService.createDonation(paymentCompleteRequest);
        assertThat(donationService.findMyDonations(member.getId(), PageRequest.of(0, 1))).hasSize(1);
    }

    @Test
    @DisplayName("findPublicDonations")
    public void findPublicDonationsTest() {
        //given
        List<DonationResponse> myDonations = donationService.findPublicDonations(member.getPageName());
        PaymentCompleteRequest paymentCompleteRequest = new PaymentCompleteRequest(payment.getImpUid(), payment.getMerchantUid().toString());

        //when
        when(paymentService.completePayment(paymentCompleteRequest))
                .thenReturn(payment);
        //then
        assertThat(myDonations).hasSize(0);

        donationService.createDonation(paymentCompleteRequest);
        List<DonationResponse> publicDonations = donationService.findPublicDonations(member.getPageName());
        assertThat(publicDonations).hasSize(1);
    }
}
