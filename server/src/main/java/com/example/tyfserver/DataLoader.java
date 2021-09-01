package com.example.tyfserver;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.donation.domain.Message;
import com.example.tyfserver.donation.repository.DonationRepository;
import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.domain.Member;
import com.example.tyfserver.member.repository.AccountRepository;
import com.example.tyfserver.member.repository.MemberRepository;
import com.example.tyfserver.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final DonationRepository donationRepository;
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    @Override
    public void run(String... args) {
        if (memberRepository.existsByNickname("로키")) {
            return;
        }

        Account account1 = accountRepository.save(new Account());
        Member member1 = new Member("Rok93@naver.com", "로키", "rokiMountain", Oauth2Type.NAVER);
        member1.addInitialAccount(account1);
        Member roki = memberRepository
                .save(member1);

        Account account2 = accountRepository.save(new Account());
        Member member2 = new Member("DWL5@kakao.com", "수리", "soorisooriMahaSoori", Oauth2Type.KAKAO);
        member2.addInitialAccount(account2);
        Member soori = memberRepository
                .save(member2);

        Account account3 = accountRepository.save(new Account());
        Member member3 = new Member("Be-poz@google.com", "파즈", "allIsBePozzible", Oauth2Type.GOOGLE);
        member3.addInitialAccount(account3);
        Member bePoz = memberRepository
                .save(member3);

        Account account4 = accountRepository.save(new Account());
        Member member4 = new Member("Joykim@naver.com", "조이", "enjoyLife", Oauth2Type.NAVER);
        member4.addInitialAccount(account4);
        Member joy = memberRepository
                .save(member4);

        Account account5 = accountRepository.save(new Account());
        Member member5 = new Member("jho2301@kakao.com", "파노", "hwanorama", Oauth2Type.KAKAO);
        member5.addInitialAccount(account5);
        Member hwano = memberRepository
                .save(member5);

        Account account6 = accountRepository.save(new Account());
        Member member6 = new Member("hchayan@google.com", "인치", "1inch", Oauth2Type.GOOGLE);
        member6.addInitialAccount(account6);
        Member inch = memberRepository
                .save(member6);

        Donation donation1 = generateDonationDummy(100_000L);
        donation1.addMessage(new Message("포비", "이 친구 코드 잘 짜네", true));
        donation1.to(roki);

        Donation donation2 = generateDonationDummy(100_000_000L);
        donation2.addMessage(new Message("제이슨", "이 정도는 제 연봉의 1/5 밖에 안된다구요!", true));
        donation2.to(bePoz);

        Donation donation3 = generateDonationDummy(9_999L);
        donation3.addMessage(new Message("구구", "9999999999", true));
        donation3.to(joy);

        Donation donation4 = generateDonationDummy(9_999L);
        donation4.addMessage(new Message("구구", "99999999", true));
        donation4.to(joy);

        Donation donation5 = generateDonationDummy(9_999L);
        donation5.addMessage(new Message("구구", "999999", true));
        donation5.to(joy);

        Donation donation6 = generateDonationDummy(10_000L);
        donation6.addMessage(new Message("포코", "당근의 세계에선.... 그 누구도 평등하죠", true));
        donation6.to(hwano);

        Donation donation7 = generateDonationDummy(50_000L);
        donation7.addMessage(new Message("공원", "갈.대.공.원", true));
        donation7.to(inch);

        Donation donation8 = generateDonationDummy(10_000L);
        donation8.addMessage(new Message("브라운", "조기수료 시켜드릴게요~!", true));
        donation8.to(soori);

        Donation donation9 = generateDonationDummy(10_000L);
        donation8.addMessage(Message.defaultMessage());
        donation8.to(soori);

        donationRepository.save(donation1);
        donationRepository.save(donation2);
        donationRepository.save(donation3);
        donationRepository.save(donation4);
        donationRepository.save(donation5);
        donationRepository.save(donation6);
        donationRepository.save(donation7);
        donationRepository.save(donation8);
        donationRepository.save(donation9);
    }

    private Donation generateDonationDummy(Long amount) {
        return new Donation(Message.defaultMessage(), 0L);
    }
}
