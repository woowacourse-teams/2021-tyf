package com.example.tyfserver.admin.controller;

import com.example.tyfserver.admin.dto.ExchangeRejectRequest;
import com.example.tyfserver.admin.dto.ExchangeResponse;
import com.example.tyfserver.admin.service.AdminService;
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

    @PostMapping("/exchange/approve")
    public ResponseEntity<Void> approveExchange(@RequestBody String pageName) {
        adminService.approveExchange(pageName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/exchange/reject")
    public ResponseEntity<Void> rejectExchange(@RequestBody ExchangeRejectRequest request) {
        adminService.rejectExchange(request.getPageName(), request.getReason());
        return ResponseEntity.ok().build();
    }
}
