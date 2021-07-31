package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentPendingRequest {

    @NotNull
    private Long amount;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String pageName;

    public PaymentPendingRequest(Long amount, String email, String pageName) {
        this.amount = amount;
        this.email = email;
        this.pageName = pageName;
    }
}
