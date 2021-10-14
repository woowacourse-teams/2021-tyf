package com.example.tyfserver.common.controller;

import com.example.tyfserver.common.service.DummyDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
@RequiredArgsConstructor
public class DummyDataController {

    private final DummyDataService dummyDataService;

    @GetMapping("/members")
    public void putMemberDummyData() {
        dummyDataService.putMemberDummyData();
    }
}
