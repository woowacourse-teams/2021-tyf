package com.example.tyfserver.banner.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import com.example.tyfserver.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Banner extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Banner(Long id, String imageUrl, Member member) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.member = member;
    }

    public Banner(Member member, String imageUrl) {
        this.member = member;
        this.imageUrl = imageUrl;
    }
}
