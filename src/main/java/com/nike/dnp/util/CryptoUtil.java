package com.nike.dnp.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * CryptoUtil
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 22. 오후 4:08:32
 * @Description 암호화 관련 Util 작성
 */
@Slf4j
@UtilityClass
public class CryptoUtil {

    /**
     * Encrypt aes 256 string.
     *
     * @param msg the msg
     * @param key the key
     * @return the string
     * @throws Exception the exception
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:08:32
     * @Description AES256 암호화
     */
    public String encryptAES256(String msg, String key) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        //byte[] saltBytes = bytes;

        // Password-Based Key Derivation function 2
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        // 70000번 해시하여 256 bit 길이의 키를 만든다.
        PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), bytes, 70000, 256);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();

        byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
        byte[] buffer = new byte[bytes.length + ivBytes.length + encryptedTextBytes.length];

        System.arraycopy(bytes, 0, buffer, 0, bytes.length);
        System.arraycopy(ivBytes, 0, buffer, bytes.length, ivBytes.length);
        System.arraycopy(encryptedTextBytes, 0, buffer, bytes.length + ivBytes.length, encryptedTextBytes.length);

        return Base64.getEncoder().encodeToString(buffer);

    }

    /**
     * Decrypt aes 256 string.
     *
     * @param msg the msg
     * @param key the key
     * @return the string
     * @throws Exception the exception
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:08:32
     * @Description AES256 복호화
     */
    public String decryptAES256(String msg, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(msg));

        byte[] bytes = new byte[20];
        buffer.get(bytes, 0, bytes.length);

        byte[] ivBytes = new byte[cipher.getBlockSize()];
        buffer.get(ivBytes, 0, ivBytes.length);

        byte[] encryoptedTextBytes = new byte[buffer.capacity() - bytes.length - ivBytes.length];
        buffer.get(encryoptedTextBytes);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), bytes, 70000, 256);
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));

        return new String(cipher.doFinal(encryoptedTextBytes));
    }

    /**
     * Url encode string.
     *
     * @param value the value
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:08:32
     * @Description Url 암호화
     */
    public String urlEncode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8");
    }

    /**
     * Url decode string.
     *
     * @param value the value
     * @return the string
     * @throws UnsupportedEncodingException the unsupported encoding exception
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:08:32
     * @Description Url 복호화
     */
    public String urlDecode(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, "UTF-8");
    }
}
