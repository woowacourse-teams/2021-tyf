package com.example.tyfserver.member.repository;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.common.config.JpaAuditingConfig;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.dto.CurationsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class MemberRepositoryImplTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    private Member testMember1;
    private Member testMember2;

    @BeforeEach
    void setUp() {
        testMember1 = memberRepository.save(MemberTest.testMember());
        testMember2 = memberRepository.save(MemberTest.testMember2());
    }

    @ParameterizedTest
    @CsvSource(value = {"tyf@gmail.com,false", "invalid,true"})
    @DisplayName("findByEmail Test")
    public void findByEmailTest(String email, boolean result) {
        //given & when
        Optional<Member> findMember = memberRepository.findByEmail(email);

        //then
        assertThat(findMember.isEmpty()).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {"tyf@gmail.com,NAVER,false", "invalid,NAVER,true", "tyf@gmail.com,GOOGLE,true"})
    @DisplayName("findByEmail Test")
    public void findByEmailAndOauth2TypeTest(String email, String oauthType, boolean result) {
        //given & when
        Optional<Member> findMember = memberRepository.findByEmailAndOauth2Type(email, Oauth2Type.findOauth2Type(oauthType));

        //then
        assertThat(findMember.isEmpty()).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {"pageName,true", "invalid,false"})
    @DisplayName("findByEmail Test")
    public void existsByPageNameTest(String pageName, boolean result) {
        //given & when & then
        assertThat(memberRepository.existsByPageName(pageName)).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {"nickname,true", "invalid,false"})
    @DisplayName("findByEmail Test")
    public void existsByNicknameTest(String nickname, boolean result) {
        //given & when & then
        assertThat(memberRepository.existsByNickname(nickname)).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {"pageName,false", "invalid,true"})
    @DisplayName("findByEmail Test")
    public void findByPageNameTest(String pageName, boolean result) {
        //given & when
        Optional<Member> findMember = memberRepository.findByPageName(pageName);

        //then
        assertThat(findMember.isEmpty()).isEqualTo(result);
    }

    @Test
    @DisplayName("curationList Test")
    public void curationListTest() {

        Member member1 = new Member("email1", "nick1", "page1", Oauth2Type.GOOGLE);
        Member member2 = new Member("email2", "nick2", "page2", Oauth2Type.GOOGLE);
        Member member3 = new Member("email3", "nick3", "page3", Oauth2Type.GOOGLE);
        Member member4 = new Member("email4", "nick4", "page4", Oauth2Type.GOOGLE);
        Member member5 = new Member("email5", "nick5", "page5", Oauth2Type.GOOGLE);
        Member member6 = new Member("email6", "nick6", "page6", Oauth2Type.GOOGLE);

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
        assertThat(curations.get(0)).usingRecursiveComparison().isEqualTo(new CurationsResponse("nick6", 13000L, "page6"));
        assertThat(curations.get(1)).usingRecursiveComparison().isEqualTo(new CurationsResponse("nick3", 7000L, "page3"));
        assertThat(curations.get(2)).usingRecursiveComparison().isEqualTo(new CurationsResponse("nick5", 5000L, "page5"));
        assertThat(curations.get(3)).usingRecursiveComparison().isEqualTo(new CurationsResponse("nick2", 2000L, "page2"));
        assertThat(curations.get(4)).usingRecursiveComparison().isEqualTo(new CurationsResponse("nick1", 1000L, "page1"));
    }
}