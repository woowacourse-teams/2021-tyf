package com.example.tyfserver;

import com.example.tyfserver.auth.domain.NaverOauth2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class YamlController {

    private final NaverOauth2 naverOauth2;

    @GetMapping("/")
    public String asdf() {
        return naverOauth2.getClientSecret();
    }
}
