package com.thankyou_for.donation.controller;

import com.thankyou_for.auth.dto.LoginMember;
import com.thankyou_for.donation.dto.DonationMessageRequest;
import com.thankyou_for.donation.dto.DonationRequest;
import com.thankyou_for.donation.dto.DonationResponse;
import com.thankyou_for.donation.exception.DonationMessageRequestException;
import com.thankyou_for.donation.exception.DonationRequestException;
import com.thankyou_for.donation.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    public ResponseEntity<DonationResponse> createDonation(@Valid @RequestBody DonationRequest donationRequest,
                                                           BindingResult result, LoginMember loginMember) {
        if (result.hasErrors()) {
            throw new DonationRequestException();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(donationService.createDonation(donationRequest, loginMember.getId()));
    }

    @PostMapping("/{donationId}/messages")
    public ResponseEntity<Void> addDonationMessage(LoginMember loginMember, @PathVariable Long donationId,
                                                   @Valid @RequestBody DonationMessageRequest donationMessageRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new DonationMessageRequestException();
        }
        donationService.addMessageToDonation(loginMember.getId(), donationId, donationMessageRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<DonationResponse>> totalDonations(LoginMember loginMember, Pageable pageable) {
        return ResponseEntity.ok(donationService.findMyDonations(loginMember.getId(), pageable));
    }

    @GetMapping("/public/{pageName}")
    public ResponseEntity<List<DonationResponse>> publicDonations(@PathVariable String pageName) {
        return ResponseEntity.ok(donationService.findPublicDonations(pageName));
    }
}
