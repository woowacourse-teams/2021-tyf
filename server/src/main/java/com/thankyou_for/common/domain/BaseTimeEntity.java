package com.thankyou_for.common.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class BaseTimeEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public BaseTimeEntity(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void createdAt() {
        if (Objects.isNull(createdAt)) {
            createdAt = LocalDateTime.now();
        }
    }
}
