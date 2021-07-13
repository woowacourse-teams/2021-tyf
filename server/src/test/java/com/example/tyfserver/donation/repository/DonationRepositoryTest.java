package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.member.MemberTest;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        final Member member = MemberTest.testMember();
        memberRepository.save(member);

        final Donation donation = new Donation(1000L);

        //when
        final Donation savedDonation = donationRepository.save(donation);

        //then
        Assertions.assertNotNull(savedDonation.getId());
    }

    @Test
    @DisplayName("해당 Member의 상위 5개의 도네이션을 가져온다.")
    public void findTop5DonationByMember() {
        //given
        Member member = MemberTest.testMember();
        memberRepository.save(member);
        //when
        Donation firstDonation = new Donation(1000L, new Message("name1", "message1", false));
        Donation secondDonation = new Donation(2000L, new Message("name2", "message2", false));
        Donation thirdDonation = new Donation(3000L, new Message("name3", "message3", false));
        Donation fourthDonation = new Donation(4000L, new Message("name4", "message4", false));
        Donation fifthDonation = new Donation(5000L, new Message("name5", "message5", true));
        Donation sixthDonation = new Donation(6000L, new Message("name6", "message6", false));
        Donation seventhDonation = new Donation(7000L, new Message("name1", "message7", false));

        firstDonation.to(member);
        secondDonation.to(member);
        thirdDonation.to(member);
        fourthDonation.to(member);
        fifthDonation.to(member);
        sixthDonation.to(member);
        seventhDonation.to(member);
        donationRepository.save(firstDonation);
        donationRepository.save(secondDonation);
        donationRepository.save(thirdDonation);
        donationRepository.save(fourthDonation);
        donationRepository.save(fifthDonation);
        donationRepository.save(sixthDonation);
        donationRepository.save(seventhDonation);

        //then
        List<Donation> donations = donationRepository.findPublicDonations(member, false, PageRequest.of(0,5, Sort.by(Sort.Direction.DESC, "createdAt")));
        assertThat(donations).containsExactlyInAnyOrder(
                 seventhDonation, secondDonation, thirdDonation, fourthDonation, sixthDonation
        );
    }

}