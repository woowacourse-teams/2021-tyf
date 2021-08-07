package com.example.tyfserver.member.domain;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.banner.domain.Banner;
import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.donation.domain.Donation;
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

    @Lob
    private String bio = DEFAULT_BIO;

    private String profileImage;

    @Embedded
    private Point point;

    @Enumerated(EnumType.STRING)
    private Oauth2Type oauth2Type;

    @Column(unique = true)
    private String pageName;

    @OneToMany(mappedBy = "member")
    private final List<Banner> banners = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<Donation> donations = new ArrayList<>();

    public Member(String email, String nickname, String pageName, Oauth2Type oauth2Type, String profileImage) {
        this.email = email;
        this.nickname = nickname;
        this.pageName = pageName;
        this.oauth2Type = oauth2Type;
        this.point = new Point(0L);
        this.profileImage = profileImage;
    }

    public Member(String email, String nickname, String pageName, Oauth2Type oauth2Type) {
        this(email, nickname, pageName, oauth2Type, null);
    }

    public void addDonation(final Donation donation) {
        this.donations.add(donation);
        donation.to(this);
        addPoint(donation.getAmount());
    }

    private void addPoint(final long amount) {
        this.point.add(amount);
    }

    public void cancelDonation(Donation donation) {
        // todo 도네이션 취소 로직 필요
        //  1. 멤버와 도네이션의 관계를 끊는다.
        //  2. 도네이션의 상태를 "취소"로 바꾼다.
        //  2-1. 페이먼트의 상태를 "CANCELLED"로 바꿨으니 그걸 통해 쿼리한다. 쿼리 겁나 복잡;
//        donation.cancel();
        reducePoint(donation.getAmount());
    }

    private void reducePoint(final long amount) {
        this.point.reduce(amount);
    }

    public void updateBio(String bio) {
        this.bio = bio;
    }

    public void updateNickName(String nickname) {
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
}
