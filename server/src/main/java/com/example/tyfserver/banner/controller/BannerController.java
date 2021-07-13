package com.example.tyfserver.banner.controller;

import com.example.tyfserver.auth.dto.LoginMember;
import com.example.tyfserver.banner.dto.BannerRequest;
import com.example.tyfserver.banner.dto.BannerResponse;
import com.example.tyfserver.banner.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @GetMapping("/me")
    public ResponseEntity<List<BannerResponse>> getBanners(LoginMember loginMember) {
        return ResponseEntity
                .ok(bannerService.getBanners(loginMember));
    }

    @PostMapping
    public ResponseEntity<Void> createBanner(LoginMember loginMember, @RequestBody BannerRequest bannerRequest) {
        bannerService.createBanner(loginMember, bannerRequest.getImageUrl());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
