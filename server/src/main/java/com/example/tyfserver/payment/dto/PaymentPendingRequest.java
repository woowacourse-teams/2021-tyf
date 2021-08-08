package com.example.tyfserver.payment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PaymentPendingRequest {

    @NotNull
    @Min(1000)
    @Max(9990000)
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
