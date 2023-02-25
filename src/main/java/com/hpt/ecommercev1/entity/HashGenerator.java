package com.hpt.ecommercev1.entity;

import com.hpt.ecommercev1.entity.exception.HashGenerationException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
    private HashGenerator() {

    }
    public static String generateMD5(String message) throws HashGenerationException {
        return hashString(message, "MD5");
    }
    private static String hashString(String message, String algorithm) throws HashGenerationException {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new HashGenerationException("Could not generate hash from String", e);
        }
    }
}
