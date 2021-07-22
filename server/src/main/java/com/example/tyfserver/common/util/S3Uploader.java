package com.example.tyfserver.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.Base64;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class S3Uploader {
    private final AmazonS3 amazonS3Client;

    @Value("${s3.bucket}")
    private String bucket;

    @Value(("${cloudfront.url}"))
    private String cloudfrontUrl;

    public S3Uploader(@Qualifier("awsS3Client") AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public String upload(MultipartFile multipartFile) throws IOException {
        System.out.println("bucket : " + bucket);
        File file = convertToFile(multipartFile);
        String fileName = System.currentTimeMillis() + Base64.encodeAsString(multipartFile.getName().getBytes()) + multipartFile.getContentType();
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file));
        file.delete();
        return cloudfrontUrl + "/" + fileName;
    }


    private File convertToFile(MultipartFile multipartFile) {
        File convertedFile = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException("파일 변환 실패!");
        }
        return convertedFile;
    }

}