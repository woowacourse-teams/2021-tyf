package com.example.tyfserver.payment.domain;

import com.example.tyfserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RefundFailure extends BaseTimeEntity {

    public static final int DEFAULT_TRY_COUNT = 10; //todo: 생성시점에 바로 감소를 시켜야해서 9부터시작해야한다!
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private int remainTryCount = DEFAULT_TRY_COUNT;

    // todo 블락된 시간도 저장해야할까?

    public RefundFailure(Integer id, int remainTryCount) {
        this.id = id;
        this.remainTryCount = remainTryCount;
    }

    public RefundFailure(int remainTryCount) {
        this(null, remainTryCount);
    }

    public void reduceTryCount() {
        remainTryCount--;
    }
}
