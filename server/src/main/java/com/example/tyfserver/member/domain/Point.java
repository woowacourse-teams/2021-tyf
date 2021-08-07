package com.example.tyfserver.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;


@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {

    private long point;

    public Point(final long point) {
        this.point = point;
    }

    public void add(final long donationAmount) {
        this.point += donationAmount;
    }

    public void reduce(final long amount) {
        this.point -= amount;
    }
}
