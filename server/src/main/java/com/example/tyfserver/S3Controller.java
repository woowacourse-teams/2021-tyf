package com.example.tyfserver;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class S3Controller {

    private final Uploader uploader;

    public S3Controller(Uploader uploader) {
        this.uploader = uploader;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("data") MultipartFile file) throws IOException {
        return uploader.upload(file);
    }
}