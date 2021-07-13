package com.example.tyfserver.donation.controller;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.donation.dto.DonationMessageRequest;
import com.example.tyfserver.donation.dto.DonationRequest;
import com.example.tyfserver.donation.dto.DonationResponse;
import com.example.tyfserver.donation.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    public ResponseEntity<DonationResponse> createDonation(@RequestBody DonationRequest donationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donationService.createDonation(donationRequest));
    }

    @PostMapping("{donationId}/messages")
    public ResponseEntity<Void> addDonationMessage(@PathVariable Long donationId,
                                                   @RequestBody DonationMessageRequest donationMessageRequest) {
        donationService.addMessageToDonation(donationId, donationMessageRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<DonationResponse>> totalDonations(LoginMember loginMember) {
        List<DonationResponse> donationResponses = donationService.findMyDonations(loginMember.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/public/{pageName}")
    public ResponseEntity<List<DonationResponse>> publicDonations(@PathVariable String pageName) {
        List<DonationResponse> donationResponses = donationService.findPublicDonations(pageName);
        return ResponseEntity.ok().build();
    }
}
