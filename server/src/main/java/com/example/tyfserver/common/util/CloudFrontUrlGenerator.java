package com.example.tyfserver.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CloudFrontUrlGenerator {

    private static String cloudfrontUrl;

    @Value("${cloudfront.url}")
    private void setCloudfrontUrl(String cloudfrontUrl) {
        CloudFrontUrlGenerator.cloudfrontUrl = cloudfrontUrl;
    }

    public static String generateUrl(String fileName) {
        return cloudfrontUrl + "/" + fileName;
    }
}
