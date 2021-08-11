package com.example.tyfserver.admin.controller;

import com.example.tyfserver.admin.dto.*;
import com.example.tyfserver.admin.service.AdminService;
import com.example.tyfserver.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/list/exchange")
    public ResponseEntity<List<ExchangeResponse>> exchangeList() {
        return ResponseEntity.ok(adminService.exchangeList());
    }

    @PostMapping("/exchange/approve/{pageName}")
    public ResponseEntity<Void> approveExchange(@PathVariable String pageName) {
        adminService.approveExchange(pageName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/exchange/reject")
    public ResponseEntity<Void> rejectExchange(@RequestBody ExchangeRejectRequest request) {
        adminService.rejectExchange(request.getPageName(), request.getReason());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/account")
    public ResponseEntity<List<RequestingAccountResponse>> requestingAccounts() {
        return ResponseEntity.ok(adminService.findRequestingAccounts());
    }

    @PostMapping("/account/approve/{memberId}")
    public ResponseEntity<Void> approveAccount(@PathVariable Long memberId) {
        adminService.approveAccount(memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/account/reject/{memberId}")
    public ResponseEntity<Void> rejectAccount(@PathVariable Long memberId,
                                              @RequestBody AccountRejectRequest accountRejectRequest) {
        adminService.rejectAccount(memberId, accountRejectRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody AdminLoginRequest adminLoginRequest) {
        return ResponseEntity.ok(adminService.login(adminLoginRequest));
    }
}
