package com.example.tyfserver.member.domain;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.member.exception.NotEnoughPointException;
import com.example.tyfserver.member.exception.WrongDonationOwnerException;
import com.example.tyfserver.payment.domain.Payment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    private static final String DEFAULT_BIO = "제 페이지에 와주셔서 감사합니다!";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    @Column(length = 1500)
    private String bio = DEFAULT_BIO;

    private String profileImage;

    @Embedded
    private Point point;

    @Enumerated(EnumType.STRING)
    private Oauth2Type oauth2Type;

    @Column(unique = true)
    private String pageName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "member")
    private final List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private final List<Donation> givenDonations = new ArrayList<>();

    @OneToMany(mappedBy = "donator")
    private final List<Donation> givingDonations = new ArrayList<>();

    public Member(String email, String nickname, String pageName, Oauth2Type oauth2Type, String profileImage,
                  Point point) {
        this.email = email;
        this.nickname = nickname;
        this.pageName = pageName;
        this.oauth2Type = oauth2Type;
        this.profileImage = profileImage;
        this.point = point;
    }

    public Member(String email, String nickname, String pageName, Oauth2Type oauth2Type, String profileImage) {
        this(email, nickname, pageName, oauth2Type, profileImage, new Point(0L));
    }

    public Member(String email, String nickname, String pageName, Oauth2Type oauth2Type) {
        this(email, nickname, pageName, oauth2Type, null);
    }

    public void receiveDonation(Donation donation) {
        this.givenDonations.add(donation);
        donation.to(this);
        increasePoint(donation.getPoint());
    }

    public void donateDonation(Donation donation) {
        this.givingDonations.add(donation);
        donation.from(this);
        reducePoint(donation.getPoint());
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.to(this);
    }

    public void updateBio(String bio) {
        this.bio = bio;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getPoint() {
        return this.point.getPoint();
    }

    public boolean isSameOauthType(String type) {
        return this.oauth2Type.name().equals(type);
    }

    public void uploadProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void deleteProfile() {
        this.profileImage = null;
    }

    public void addInitialAccount(Account account) {
        if (this.account != null) {
            return;
        }

        this.account = account;
    }

    public void registerAccount(String accountHolder, String accountNumber, String bank, String bankBookUrl) {
        this.account.register(accountHolder, accountNumber, bank, bankBookUrl);
    }

    public void validateEnoughPoint(Long point) {
        if (this.point.lessThan(point)) {
            throw new NotEnoughPointException();
        }
    }

    public AccountStatus getAccountStatus() {
        return this.account.getStatus();
    }

    public void increasePoint(Long amount) {
        this.point.add(amount);
    }

    public void reducePoint(long amount) {
        this.point.reduce(amount);
    }

    public void approveAccount() {
        this.account.approve();
    }

    public void rejectAccount() {
        this.account.reject();
    }

    public String getBankBookUrl() {
        return this.account.getBankbookUrl();
    }

    public void addAvailablePoint(Long amount) {
        this.point.add(amount);
    }

    public void validateMemberGivingDonation(Donation donation) {
        if (this.getGivingDonations().contains(donation)) {
            return;
        }
        throw new WrongDonationOwnerException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
