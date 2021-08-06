package com.example.tyfserver.payment.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundFailure extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private int remainTryCount;

    // todo 블락된 시간도 저장해야할까?

    public RefundFailure(Integer id, int remainTryCount) {
        this.id = id;
        this.remainTryCount = remainTryCount;
    }

    public RefundFailure(int remainTryCount) {
        this(null, remainTryCount);
    }
}
