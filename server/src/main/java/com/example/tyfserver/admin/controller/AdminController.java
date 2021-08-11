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

    @PostMapping("/approve/{member_id}/account")
    public ResponseEntity<Void> approveAccount(@PathVariable("member_id") Long member_id) {
        adminService.approveAccount(member_id);;
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel/{member_id}/account")
    public ResponseEntity<Void> cancelAccount(@PathVariable("member_id") Long member_id,
                                              AccountCancelRequest accountCancelRequest) {
        adminService.cancelAccount(member_id, accountCancelRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list/account")
    public ResponseEntity<List<RequestingAccountResponse>> requestingAccounts() {
        return ResponseEntity.ok(adminService.findRequestingAccounts());
    }
}
