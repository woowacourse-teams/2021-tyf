package com.example.tyfserver.member.domain;

import com.example.tyfserver.member.exception.NotEnoughPointException;
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
        if (amount > point) {
            throw new NotEnoughPointException();
        }
        this.point -= amount;
    }

    public boolean lessThan(Long point) {
        return this.point < point;
    }
}
