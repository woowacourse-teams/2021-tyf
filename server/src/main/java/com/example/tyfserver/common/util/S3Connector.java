package com.example.tyfserver.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.Base64;
import com.example.tyfserver.common.exception.S3FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class S3Connector {

    private final AmazonS3 awsS3Client;

    @Value("${s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, Long memberId) {
        File file = convertToFile(multipartFile);
        String fileName = System.currentTimeMillis() + "_" +
                Base64.encodeAsString(memberId.byteValue()) + multipartFile.getContentType();
        awsS3Client.putObject(new PutObjectRequest(bucket, fileName, file));
        file.delete();

        return CloudFrontUrlGenerator.generateUrl(fileName);
    }

    public void delete(String fileName) {
        if (awsS3Client.doesObjectExist(bucket, fileName)) {
            awsS3Client.deleteObject(bucket, fileName);
        }
        throw new S3FileNotFoundException();
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

}