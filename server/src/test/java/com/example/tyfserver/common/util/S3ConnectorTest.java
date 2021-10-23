package com.example.tyfserver.common.util;

import org.apache.http.entity.ContentType;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class S3ConnectorTest {

    Tika tika = new Tika();

    @Test
    public void extension() throws IOException, MimeTypeException {
        MockMultipartFile file = new MockMultipartFile("sampleText",
                "avisofjewoifj3i12j3ju540984fjfasfkladfs.txt",
                ContentType.TEXT_PLAIN.getMimeType(), "hello".getBytes());

        MimeTypes mTypes = MimeTypes.getDefaultMimeTypes();
        MimeType mimeType = mTypes.forName(
                tika.detect(file.getBytes())
        );
        assertThat(mimeType.getExtension()).isEqualTo(".txt");
    }
}