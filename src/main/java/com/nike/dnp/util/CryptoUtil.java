package com.nike.dnp.util;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.exception.CodeMessageHandleException;
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
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:08:32
     * @Description AES256 암호화
     */
    public String encryptAES256(String msg, String key) {
        try {
            final SecureRandom random = new SecureRandom();
            final byte[] _bytes = new byte[20];
            random.nextBytes(_bytes);
            final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            // 70000번 해시하여 256 bit 길이의 키를 만든다.
            final PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), _bytes, 70000, 256);
            final SecretKey secretKey = factory.generateSecret(spec);
            final SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            final AlgorithmParameters params = cipher.getParameters();

            final byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
            final byte[] encryptedTextBytes = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
            final byte[] buffer = new byte[_bytes.length + ivBytes.length + encryptedTextBytes.length];

            System.arraycopy(_bytes, 0, buffer, 0, _bytes.length);
            System.arraycopy(ivBytes, 0, buffer, _bytes.length, ivBytes.length);
            System.arraycopy(encryptedTextBytes, 0, buffer, _bytes.length + ivBytes.length, encryptedTextBytes.length);

            return Base64.getEncoder().encodeToString(buffer);
        } catch (Exception exception) {
            throw new CodeMessageHandleException(
                    FailCode.ExceptionError.ERROR.name()
                    , exception.getMessage());
        }
    }

    /**
     * Decrypt aes 256 string.
     *
     * @param msg the msg
     * @param key the key
     * @return the string
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:08:32
     * @Description AES256 복호화
     */
    public String decryptAES256(String msg, String key) {
        try {
            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(msg));

            final byte[] _bytes = new byte[20];
            buffer.get(_bytes, 0, _bytes.length);

            final byte[] ivBytes = new byte[cipher.getBlockSize()];
            buffer.get(ivBytes, 0, ivBytes.length);

            final byte[] encryoptedTextBytes = new byte[buffer.capacity() - _bytes.length - ivBytes.length];
            buffer.get(encryoptedTextBytes);

            final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            final PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), _bytes, 70000, 256);
            final SecretKey secretKey = factory.generateSecret(spec);
            final SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));

            return new String(cipher.doFinal(encryoptedTextBytes));
        } catch (Exception exception) {
            throw new CodeMessageHandleException(
                    FailCode.ExceptionError.ERROR.name()
                    , exception.getMessage());
        }
    }

    /**
     * Url encode string.
     *
     * @param value the value
     * @return the string
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:08:32
     * @Description Url 암호화
     */
    public String urlEncode(String value)  {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException exception) {
            throw new CodeMessageHandleException(
                    FailCode.ExceptionError.ERROR.name()
                    , exception.getMessage());
        }
    }

    /**
     * Url decode string.
     *
     * @param value the value
     * @return the string
     * @author [오지훈]
     * @CreatedOn 2020. 6. 22. 오후 4:08:32
     * @Description Url 복호화
     */
    public String urlDecode(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException exception) {
            throw new CodeMessageHandleException(
                    FailCode.ExceptionError.ERROR.name()
                    , exception.getMessage());
        }
    }
}
