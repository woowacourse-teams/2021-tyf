package com.example.tyfserver.admin.controller;

import com.example.tyfserver.admin.dto.AccountRejectRequest;
import com.example.tyfserver.admin.dto.RequestingAccountResponse;
import com.example.tyfserver.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.tyfserver.admin.dto.AdminLoginRequest;
import com.example.tyfserver.admin.service.AdminService;
import com.example.tyfserver.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/approve/{memberId}/account")
    public ResponseEntity<Void> approveAccount(@PathVariable Long memberId) {
        adminService.approveAccount(memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject/{memberId}/account")
    public ResponseEntity<Void> rejectAccount(@PathVariable Long memberId,
                                              AccountRejectRequest accountRejectRequest) {
        adminService.rejectAccount(memberId, accountRejectRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/account")
    public ResponseEntity<List<RequestingAccountResponse>> requestingAccounts() {
        return ResponseEntity.ok(adminService.findRequestingAccounts());
    }
    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody AdminLoginRequest adminLoginRequest) {
        return ResponseEntity.ok(adminService.login(adminLoginRequest));
    }
}