package com.thankyou_for.admin.dto;

import com.thankyou_for.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<RequestingAccountResponse> toList(List<Member> members) {
        return members.stream()
                .map(member -> new RequestingAccountResponse(member.getId(), member.getEmail(),
                        member.getNickname(), member.getPageName(), member.getAccount().getAccountHolder(),
                        member.getAccount().getAccountNumber(), member.getAccount().getBank(), member.getBankBookUrl()))
                .collect(Collectors.toList());
    }
}
