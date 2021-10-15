package com.example.tyfserver.payment.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class RefundFailure extends BaseTimeEntity {

    public static final int DEFAULT_TRY_COUNT = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int remainTryCount;

    public RefundFailure(Long id, int remainTryCount) {
        this.id = id;
        this.remainTryCount = remainTryCount;
    }

    public RefundFailure(int remainTryCount) {
        this(null, remainTryCount);
    }

    public RefundFailure() {
        this(DEFAULT_TRY_COUNT);
    }

    public boolean isBlocked() {
        return remainTryCount == 0;
    }

    public void reduceTryCount() {
        remainTryCount--;
    }
}
