package com.thankyou_for.auth.service;

import com.thankyou_for.auth.dto.IdAndEmail;
import com.thankyou_for.auth.dto.LoginMember;
import com.thankyou_for.auth.dto.VerifiedRefunder;
import com.thankyou_for.auth.util.JwtTokenProvider;
import com.thankyou_for.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtTokenProvider jwtTokenProvider;

    public String createToken(Member member) {
        return jwtTokenProvider.createToken(member.getId(), member.getEmail());
    }

    public String createRefundToken(String merchantUid) {
        return jwtTokenProvider.createRefundToken(merchantUid);
    }

    public String createAdminToken(String id) {
        return jwtTokenProvider.createAdminToken(id);
    }

    public LoginMember createLoginMemberByToken(String token) {
        IdAndEmail idAndEmail = jwtTokenProvider.findIdAndEmailFromToken(token);
        return new LoginMember(idAndEmail.getId(), idAndEmail.getEmail());
    }

    public VerifiedRefunder createVerifiedRefundRequestByToken(String token) {
        String merchantUid = jwtTokenProvider.findMerchantUidFromToken(token);
        return new VerifiedRefunder(merchantUid);
    }

    public void validateToken(String token) {
        jwtTokenProvider.validateToken(token);
    }
}
