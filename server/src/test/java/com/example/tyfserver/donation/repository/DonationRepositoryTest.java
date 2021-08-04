package com.example.tyfserver.donation.repository;

import com.example.tyfserver.common.config.JpaAuditingConfig;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.domain.Payment;
import com.example.tyfserver.payment.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class DonationRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    private Member member;
    private Donation donation1;
    private Donation donation2;
    private Donation donation3;
    private Donation donation4;
    private Donation donation5;
    private Donation donation6;
    private Donation donation7;

    @BeforeEach
    void setUp() {
        member = MemberTest.testMember();
        memberRepository.save(member);
        //when
        donation1 = new Donation(
                paymentRepository.save(new Payment(1000L, "test@test.com", "test")),
                new Message("name1", "message1", false)
        );
        donation2 = new Donation(
                paymentRepository.save(new Payment(2000L, "test@test.com", "test")),
                new Message("name2", "message2", false)
        );
        donation3 = new Donation(
                paymentRepository.save(new Payment(3000L, "test@test.com", "test")),
                new Message("name3", "message3", false)
        );
        donation4 = new Donation(
                paymentRepository.save(new Payment(4000L, "test@test.com", "test")),
                new Message("name4", "message4", false)
        );
        donation5 = new Donation(
                paymentRepository.save(new Payment(5000L, "test@test.com", "test")),
                new Message("name5", "message5", true));
        donation6 = new Donation(
                paymentRepository.save(new Payment(6000L, "test@test.com", "test")),
                new Message("name6", "message6", false));
        donation7 = new Donation(
                paymentRepository.save(new Payment(7000L, "test@test.com", "test")),
                new Message("name1", "message7", false));

        donation1.to(member);
        donation2.to(member);
        donation3.to(member);
        donation4.to(member);
        donation5.to(member);
        donation6.to(member);
        donation7.to(member);
        donationRepository.save(donation1);
        donationRepository.save(donation2);
        donationRepository.save(donation3);
        donationRepository.save(donation4);
        donationRepository.save(donation5);
        donationRepository.save(donation6);
        donationRepository.save(donation7);
    }

    @Test
    @DisplayName("해당 Member가 받은 secret false인 최신 5개의 도네이션을 가져온다.")
    public void findPublicDonations() {

        List<Donation> donations = donationRepository.findPublicDonations(member, false, PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt")));
        assertThat(donations).containsExactlyInAnyOrder(
                donation7, donation2, donation3, donation4, donation6
        );
    }

    @Test
    @DisplayName("해당 Member가 받은 최신 5개의 도네이션을 가져온다.")
    public void findTop5ByMember() {
        List<Donation> donations = donationRepository.findFirst5ByMemberOrderByCreatedAtDesc(member);
        assertThat(donations).containsExactlyInAnyOrder(
                donation7, donation6, donation5, donation4, donation3
        );
    }

    @Test
    @DisplayName("해당 Member가 받은 최신 도네이션을 가져온다. size 3에 두 번째 page인 경우")
    public void findDonationByMemberOrderByCreatedAtDesc() {
        List<Donation> donations = donationRepository.findDonationByMemberOrderByCreatedAtDesc(
                member, PageRequest.of(1, 3));
        assertThat(donations).containsExactlyInAnyOrder(
                donation4, donation3, donation2
        );
    }
}
