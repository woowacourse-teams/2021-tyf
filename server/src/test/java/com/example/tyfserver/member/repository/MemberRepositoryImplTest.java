package com.example.tyfserver.member.repository;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.dto.CurationsResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryImplTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void curationListTest() {

        Member member1 = new Member("email1", "nickname1", "pageName1", Oauth2Type.GOOGLE);
        Member member2 = new Member("email2", "nickname2", "pageName2", Oauth2Type.GOOGLE);
        Member member3 = new Member("email3", "nickname3", "pageName3", Oauth2Type.GOOGLE);
        Member member4 = new Member("email4", "nickname4", "pageName4", Oauth2Type.GOOGLE);
        Member member5 = new Member("email5", "nickname5", "pageName5", Oauth2Type.GOOGLE);
        Member member6 = new Member("email6", "nickname6", "pageName6", Oauth2Type.GOOGLE);

        Donation donation1 = new Donation(1000L, Message.defaultMessage());
        Donation donation2 = new Donation(2000L, Message.defaultMessage());
        Donation donation3 = new Donation(3000L, Message.defaultMessage());
        Donation donation4 = new Donation(4000L, Message.defaultMessage());
        Donation donation5 = new Donation(5000L, Message.defaultMessage());
        Donation donation6 = new Donation(6000L, Message.defaultMessage());
        Donation donation7 = new Donation(7000L, Message.defaultMessage());

        //member1 1000 member2 2000, member3 7000, member4 0, member5 5000, member6 13000
        member1.addDonation(donation1);
        member2.addDonation(donation2);
        member3.addDonation(donation3);
        member3.addDonation(donation4);
        member5.addDonation(donation5);
        member6.addDonation(donation6);
        member6.addDonation(donation7);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
        em.persist(member5);
        em.persist(member6);
        em.persist(donation1);
        em.persist(donation2);
        em.persist(donation3);
        em.persist(donation4);
        em.persist(donation5);
        em.persist(donation6);
        em.persist(donation7);
        em.flush();
        em.clear();

        List<CurationsResponse> curations = memberRepository.findCurations();
        assertThat(curations.get(0)).usingRecursiveComparison().isEqualTo(new CurationsResponse("nickname6", 13000L, "pageName6"));
        assertThat(curations.get(1)).usingRecursiveComparison().isEqualTo(new CurationsResponse("nickname3", 7000L, "pageName3"));
        assertThat(curations.get(2)).usingRecursiveComparison().isEqualTo(new CurationsResponse("nickname5", 5000L, "pageName5"));
        assertThat(curations.get(3)).usingRecursiveComparison().isEqualTo(new CurationsResponse("nickname2", 2000L, "pageName2"));
        assertThat(curations.get(4)).usingRecursiveComparison().isEqualTo(new CurationsResponse("nickname1", 1000L, "pageName111111"));
    }

}