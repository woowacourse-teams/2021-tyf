package com.example.tyfserver.member.repository;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.common.config.JpaAuditingConfig;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.MessageTest;
import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.domain.MemberTest;
import com.example.tyfserver.member.dto.CurationsResponse;
import com.example.tyfserver.payment.repository.PaymentRepository;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class MemberRepositoryImplTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Member testMember1;
    private Member testMember2;

    @BeforeEach
    void setUp() {
        testMember1 = memberRepository.save(MemberTest.testMember());
        testMember2 = memberRepository.save(MemberTest.testMember2());
    }


    @Test
    @DisplayName("자기소개는 500자 까지만 저장가능하다.")
    public void InvalidBio() {
        //when, then
        assertThatThrownBy(() -> {
            testMember1.updateBio("테스트".repeat(1000));
            em.flush();
        }).isInstanceOf(RuntimeException.class);
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
    @DisplayName("existsByPageName Test")
    public void existsByPageNameTest(String pageName, boolean result) {
        //given & when & then
        assertThat(memberRepository.existsByPageName(pageName)).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {"nickname,true", "invalid,false"})
    @DisplayName("existsByPageName Test")
    public void existsByNicknameTest(String nickname, boolean result) {
        //given & when & then
        assertThat(memberRepository.existsByNickname(nickname)).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvSource(value = {"pageName,false", "invalid,true"})
    @DisplayName("findByPageName Test")
    public void findByPageNameTest(String pageName, boolean result) {
        //given & when
        Optional<Member> findMember = memberRepository.findByPageName(pageName);

        //then
        assertThat(findMember.isEmpty()).isEqualTo(result);
    }

    @Test
    @DisplayName("curationList Test")
    public void curationListTest() {
        // given
        Member member1 = initMember(1); // 1000
        Member member2 = initMember(2); // 2000
        Member member3 = initMember(3); // 7000 (3000, 4000)
        Member member4 = initMember(4); // 0
        Member member5 = initMember(5); // 5000
        Member member6 = initMember(6); // 13000 (6000, 7000)

        initDonation(member1, 1000L);
        initDonation(member2, 2000L);
        initDonation(member3, 3000L);
        initDonation(member3, 4000L);
        initDonation(member5, 5000L);
        initDonation(member6, 6000L);
        initDonation(member6, 7000L);

        // todo 도네이션 합계 쿼리시 CANCELLED 도 포함해서 계산중임. 정상적인 도네이션만 쿼리 필요
//        member2.getDonations().get(0).toCancelled();

        em.flush();
        em.clear();

        // when
        List<CurationsResponse> curations = memberRepository.findCurations();

        // then
        assertThat(curations.get(0)).usingRecursiveComparison().isEqualTo(
                curationsResponseFromMember(member6, 13000L)
        );

        assertThat(curations.get(1)).usingRecursiveComparison().isEqualTo(
                curationsResponseFromMember(member3, 7000L)
        );

        assertThat(curations.get(2)).usingRecursiveComparison().isEqualTo(
                curationsResponseFromMember(member5, 5000L)
        );

        assertThat(curations.get(3)).usingRecursiveComparison().isEqualTo(
                curationsResponseFromMember(member2, 2000L)
        );

        assertThat(curations.get(4)).usingRecursiveComparison().isEqualTo(
                curationsResponseFromMember(member1, 1000L)
        );

        // todo 개선 되면 위 두개 지우고 이거 통과해야함
//        assertThat(curations.get(3)).usingRecursiveComparison().isEqualTo(
//                curationsResponseFromMember(member1, 1000L)
//        );
    }

    private Member initMember(int i) {
        Member member = new Member("email" + i, "nick" + i, "page" + i,
                Oauth2Type.GOOGLE, "https://cloudfront.net/profile" + i + ".png");
        member.updateBio("I am test");
        em.persist(member);
        return member;
    }

    private void initDonation(Member member, long amount) {
        Donation donation = new Donation(MessageTest.testMessage(), amount);
        member.addDonation(donation);
        em.persist(donation);
    }

    private CurationsResponse curationsResponseFromMember(Member member6, long donationAmount) {
        return new CurationsResponse(member6.getNickname(), donationAmount, member6.getPageName(),
                member6.getProfileImage(), member6.getBio());
    }

    @Test
    @DisplayName("정산 계좌 승인 요청 리스트를 얻어온다.")
    public void findRequestingAccounts() {

        Member member1 = new Member("email1", "nick1", "page1",
                Oauth2Type.GOOGLE, "https://cloudfront.net/profile1.png");

        Account defaultAccount1 = new Account();
        em.persist(defaultAccount1);
        member1.addInitialAccount(defaultAccount1);
        member1.registerAccount("테스트유저1", "1234-5678-1231",
                "하나", "https://cloudfront.net/bankbook.png");

        Member member2 = new Member("email2", "nick2", "page2",
                Oauth2Type.GOOGLE, "https://cloudfront.net/profile2.png");

        Account defaultAccount2 = new Account();
        em.persist(defaultAccount2);
        member2.addInitialAccount(defaultAccount2);

        Member member3 = new Member("email3", "nick3", "page3",
                Oauth2Type.GOOGLE, "https://cloudfront.net/profile3.png");

        Account defaultAccount3 = new Account();
        em.persist(defaultAccount3);
        member3.addInitialAccount(defaultAccount3);
        member3.registerAccount("테스트유저1", "1234-5678-1233",
                "하나", "https://cloudfront.net/bankbook.png");

        Member member4 = new Member("email4", "nick4", "page4",
                Oauth2Type.GOOGLE, "https://cloudfront.net/profile4.png");

        Account defaultAccount4 = new Account();
        em.persist(defaultAccount4);
        member4.addInitialAccount(defaultAccount4);

        Member member5 = new Member("email5", "nick5", "page5",
                Oauth2Type.GOOGLE, "https://cloudfront.net/profile5.png");

        Account defaultAccount5 = new Account();
        member5.addInitialAccount(defaultAccount5);
        em.persist(defaultAccount5);
        member5.registerAccount("테스트유저1", "1234-5678-1236",
                "하나", "https://cloudfront.net/bankbook.png");

        Member member6 = new Member("email6", "nick6", "page6",
                Oauth2Type.GOOGLE, "https://cloudfront.net/profile6.png");
        Account defaultAccount6 = new Account();
        em.persist(defaultAccount6);
        member6.addInitialAccount(defaultAccount6);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
        em.persist(member5);
        em.persist(member6);

        List<Member> requestingAccounts = memberRepository.findRequestingAccounts();
        assertThat(requestingAccounts).hasSize(3);
        Member selectedMember = requestingAccounts.get(0);
        assertThat(selectedMember.getEmail()).isEqualTo(member1.getEmail());
        assertThat(selectedMember.getNickname()).isEqualTo(member1.getNickname());
        assertThat(selectedMember.getPageName()).isEqualTo(member1.getPageName());
        assertThat(selectedMember.getAccount().getAccountHolder()).isEqualTo(member1.getAccount().getAccountHolder());
        assertThat(selectedMember.getAccount().getAccountNumber()).isEqualTo(member1.getAccount().getAccountNumber());
        assertThat(selectedMember.getAccount().getBank()).isEqualTo(member1.getAccount().getBank());
        assertThat(selectedMember.getAccount().getBankbookUrl()).isEqualTo(member1.getAccount().getBankbookUrl());
    }
}