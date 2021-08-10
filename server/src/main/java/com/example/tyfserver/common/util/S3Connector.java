package com.example.tyfserver.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.tyfserver.common.exception.S3FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Connector {

    private final AmazonS3 awsS3Client;

    @Value("${cloudfront.url}")
    private String cloudfrontUrl;

    @Value("${s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String path) {
        File file = convertToFile(multipartFile);
        String fileName = path + UUID.randomUUID() + multipartFile.getOriginalFilename();
        awsS3Client.putObject(new PutObjectRequest(bucket, fileName, file));
        file.delete();

        return cloudfrontUrl + fileName;
    }

    public void delete(String fileName) {
        String key = detachCloudFrontUrl(fileName);
        if (awsS3Client.doesObjectExist(bucket, key)) {
            awsS3Client.deleteObject(bucket, key);
            return;
        }
        throw new S3FileNotFoundException(key);
    }

    private File convertToFile(MultipartFile multipartFile) {
        File convertedFile = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException("파일 변환에 실패하였습니다!");
        }
        return convertedFile;
    }

    public String detachCloudFrontUrl(String fileName) {
        return fileName.split("cloudfront.net/")[1];
    }
}