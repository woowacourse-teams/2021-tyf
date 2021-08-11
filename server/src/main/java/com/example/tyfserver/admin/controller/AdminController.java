package com.example.tyfserver.admin.controller;

import com.example.tyfserver.admin.dto.AccountCancelRequest;
import com.example.tyfserver.admin.dto.RequestingAccountResponse;
import com.example.tyfserver.admin.service.AdminService;
import com.example.tyfserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/approve/{memberId}/account")
    public ResponseEntity<Void> approveAccount(Long memberId) {
        adminService.approveAccount(memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel/{memberId}/account")
    public ResponseEntity<Void> cancelAccount(Long memberId,
                                              AccountCancelRequest accountCancelRequest) {
        adminService.cancelAccount(memberId, accountCancelRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/account")
    public ResponseEntity<List<RequestingAccountResponse>> requestingAccounts() {
        return ResponseEntity.ok(adminService.findRequestingAccounts());
    }
}
