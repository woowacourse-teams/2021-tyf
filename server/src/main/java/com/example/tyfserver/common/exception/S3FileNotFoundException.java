package com.example.tyfserver.common.exception;

public class S3FileNotFoundException extends BaseException{

    public static final String ERROR_CODE = "aws-001";
    private static final String MESSAGE = "S3 스토리지에서 해당 파일을 찾을 수 없습니다.";

    public S3FileNotFoundException() {
        super(ERROR_CODE, MESSAGE);
    }

    public S3FileNotFoundException(String fileName) {
        super(ERROR_CODE, MESSAGE + " fileName : " + fileName);
    }
}
