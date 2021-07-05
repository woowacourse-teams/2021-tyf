package com.example.tyfserver.member.domain;

import com.example.tyfserver.banner.domain.Banner;
import com.example.tyfserver.donation.domain.Donation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    // todo Point VO 감싸기(최소값 0)
    private BigInteger point;

    @OneToMany(mappedBy = "member")
    private List<Banner> banners = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Donation> donations = new ArrayList<>();

    public Member(String email) {
        this.email = email;
        this.point = BigInteger.valueOf(0L);
    }
}
