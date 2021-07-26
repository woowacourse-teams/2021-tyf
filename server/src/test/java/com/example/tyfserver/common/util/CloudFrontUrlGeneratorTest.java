package com.example.tyfserver.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CloudFrontUrlGeneratorTest {

    @Test
    @DisplayName("CloudFrontUrl 생성 확인")
    public void generateUrlTest() {
        String fileName = "fileName";
        assertThat(CloudFrontUrlGenerator.generateUrl(fileName)).isEqualTo("https://de56jrhz7aye2.cloudfront.net/" + fileName);
    }
}