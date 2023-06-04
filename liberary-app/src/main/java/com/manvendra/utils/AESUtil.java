package com.manvendra.utils;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;

@Service
public class AESUtil {
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_SECRET_KEY = "your-secret-key1234567890"; // Replace with your secret key

    public  String encrypt(String plainText) {
        try {
            // Pad the key if it's shorter than 32 bytes
            byte[] keyBytes = Arrays.copyOf(AES_SECRET_KEY.getBytes(StandardCharsets.UTF_8), 32);

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            // Handle encryption error
            throw new RuntimeException("Failed to encrypt the plain text", e);
        }
    }


    public String decrypt(String encryptedCredentials) {
        try {
            byte[] keyBytes = Arrays.copyOf(AES_SECRET_KEY.getBytes(StandardCharsets.UTF_8), 32);

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedCredentials));
            return new String(decryptedBytes, StandardCharsets.UTF_8);

        } catch (IllegalArgumentException | GeneralSecurityException e) {
            // Handle decryption error
            throw new RuntimeException("Failed to decrypt the encrypted credentials: " + e.getMessage(), e);
        }
    }
}
