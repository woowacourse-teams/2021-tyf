package com.example.tyfserver.member.domain;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.member.exception.NotEnoughPointException;
import com.example.tyfserver.payment.domain.Payment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @AttributeOverride(name = "point", column = @Column(name = "available_point"))
    private Point availablePoint;

    @Enumerated(EnumType.STRING)
    private Oauth2Type oauth2Type;

    @Column(unique = true)
    private String pageName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "member")
    private final List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<Donation> donations = new ArrayList<>();

    public Member(String email, String nickname, String pageName, Oauth2Type oauth2Type, String profileImage,
                  Point availablePoint) {
        this.email = email;
        this.nickname = nickname;
        this.pageName = pageName;
        this.oauth2Type = oauth2Type;
        this.profileImage = profileImage;
        this.availablePoint = availablePoint;
    }

    public Member(String email, String nickname, String pageName, Oauth2Type oauth2Type, String profileImage) {
        this(email, nickname, pageName, oauth2Type, profileImage, new Point(0L));
    }

    public Member(String email, String nickname, String pageName, Oauth2Type oauth2Type) {
        this(email, nickname, pageName, oauth2Type, null);
    }

    public void addDonation(Donation donation) {
        this.donations.add(donation);
        donation.to(this);
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

    public long getAvailablePoint() {
        return this.availablePoint.getPoint();
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
        if (availablePoint.lessThan(point)) {
            throw new NotEnoughPointException();
        }
    }

    public AccountStatus getAccountStatus() {
        return this.account.getStatus();
    }

    public void reducePoint(long amount) {
        availablePoint.reduce(amount);
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
        this.availablePoint.add(amount);
    }
}
