package com.thankyou_for.admin.controller;

import com.thankyou_for.admin.service.AdminService;
import com.thankyou_for.auth.dto.TokenResponse;
import com.thankyou_for.admin.dto.*;
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
