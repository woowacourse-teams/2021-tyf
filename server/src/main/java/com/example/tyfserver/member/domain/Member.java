package com.example.tyfserver.member.domain;

import com.example.tyfserver.auth.domain.OAuth2Type;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickName;

    @Embedded
    private Point point;

    @Enumerated(EnumType.STRING)
    private OAuth2Type oAuth2Type;

    @Column(unique = true)
    private String ownPageUrl;

    @OneToMany(mappedBy = "member")
    private final List<Banner> banners = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private final List<Donation> donations = new ArrayList<>();

    public Member(String email, String nickName, String ownPageUrl, OAuth2Type oAuth2Type) {
        this.email = email;
        this.nickName = nickName;
        this.oAuth2Type = oAuth2Type;
        this.point = new Point(0L);
    }

    public void addDonation(final Donation donation) {
        this.donations.add(donation);
        donation.to(this);
    }

    public void addPoint(final long donationAmount) {
        this.point.add(donationAmount);
    }

    public boolean isSameOAuthType(String type) {
        return this.oAuth2Type.name().equals(type);
    }
}
