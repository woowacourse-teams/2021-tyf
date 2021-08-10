package com.example.tyfserver.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestingAccountResponse {

    private Long memberId;
    private String email;
    private String nickname;
    private String pageName;
    private String accountHolder;
    private String accountNumber;
    private String bank;
    private String bankbookImageUrl;


    @QueryProjection
    public RequestingAccountResponse(Long memberId, String email, String nickname, String pageName, String accountHolder,
                                     String accountNumber, String bank, String bankbookImageUrl) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.pageName = pageName;
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.bankbookImageUrl = bankbookImageUrl;
    }
}
