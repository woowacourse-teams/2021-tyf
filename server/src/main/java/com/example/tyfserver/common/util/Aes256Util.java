package com.example.tyfserver.common.util;

import com.example.tyfserver.common.exception.DecryptException;
import com.example.tyfserver.common.exception.EncryptException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class Aes256Util {
    @Value("${aes.secret}")
    private String secretKey;

    public String encrypt(String data) {
        try {
            byte[] keyData = secretKey.getBytes(StandardCharsets.UTF_8);
            byte[] ivData = secretKey.substring(0, 16).getBytes(StandardCharsets.UTF_8);
            SecretKey secretKey = new SecretKeySpec(keyData, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(ivData));

            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(encrypted));
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException
                | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e) {
            throw new EncryptException();
        }
    }

    public String decrypt(String encryptedData) {
        byte[] keyData;
        try {
            keyData = secretKey.getBytes(StandardCharsets.UTF_8);
            byte[] ivData = secretKey.substring(0, 16).getBytes(StandardCharsets.UTF_8);
            SecretKey secretKey = new SecretKeySpec(keyData, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(ivData));
            byte[] decrypted = Base64.getDecoder().decode(encryptedData.getBytes(StandardCharsets.UTF_8));
            return new String(cipher.doFinal(decrypted), StandardCharsets.UTF_8);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException
                | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            throw new DecryptException();
        }
    }
}
