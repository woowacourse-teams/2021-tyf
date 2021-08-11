package com.example.tyfserver.admin.controller;

import com.example.tyfserver.admin.dto.ExchangeRejectRequest;
import com.example.tyfserver.admin.dto.ExchangeResponse;
import com.example.tyfserver.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.tyfserver.admin.dto.AdminLoginRequest;
import com.example.tyfserver.auth.dto.TokenResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody AdminLoginRequest adminLoginRequest) {
        return ResponseEntity.ok(adminService.login(adminLoginRequest));
    }
}
