package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DonationRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DonationRepository donationRepository;

    @DisplayName("후원을 등록한다.")
    @Test
    void createDonation() throws Exception {
        //given
        final Member member = new Member("tyf@gmail.com");
        memberRepository.save(member);

        final Donation donation = new Donation(1000L);

        //when
        final Donation savedDonation = donationRepository.save(donation);

        //then
        Assertions.assertNotNull(savedDonation.id());
    }

}