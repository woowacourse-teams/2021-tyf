package com.example.tyfserver.admin.service;

import com.example.tyfserver.admin.domain.AdminAccount;
import com.example.tyfserver.admin.dto.AdminLoginRequest;
import com.example.tyfserver.auth.dto.TokenResponse;
import com.example.tyfserver.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminAccount adminAccount;
    private final AuthenticationService authenticationService;

    public TokenResponse login(AdminLoginRequest adminLoginRequest) {
        adminAccount.validateLogin(adminLoginRequest.getId(), adminLoginRequest.getPassword());
        String token = authenticationService.createAdminToken();// todo: adminPage  accessToken의 payload는 뭘 줘야할까....?
        return new TokenResponse(token);
    }
}
