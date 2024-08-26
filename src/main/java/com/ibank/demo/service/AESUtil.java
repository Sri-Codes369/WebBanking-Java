package com.ibank.demo.service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtil {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String CHARSET = "UTF-8";

    public static String encrypt(String value, String secretKey) throws Exception {
        logger.info(secretKey);
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(CHARSET), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedValue = cipher.doFinal(value.getBytes(CHARSET));
        logger.info(Base64.getEncoder().encodeToString(encryptedValue));
        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    public static String decrypt(String value, String secretKey) throws Exception {
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(CHARSET), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue = cipher.doFinal(Base64.getDecoder().decode(value));
        return new String(decryptedValue);
    }
}

