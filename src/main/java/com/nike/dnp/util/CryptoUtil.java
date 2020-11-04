package com.nike.dnp.util;

import com.nike.dnp.common.variable.FailCode;
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
 * @implNote 암호화 관련 Util 작성
 * @since 2020. 6. 22. 오후 4:08:32
 */
@Slf4j
@UtilityClass
public class CryptoUtil {

    /**
     * The constant HASH_CYCLE
     *
     * @author [오지훈]
     */
    private final static int HASH_CYCLE = 70000;

    /**
     * The constant HASH_BIT
     *
     * @author [오지훈]
     */
    private final static int HASH_BIT = 256;

    /**
     * The constant CIPHER_INSTANCE
     *
     * @author [오지훈]
     */
    private final static String CIPHER_INSTANCE = "AES/CBC/PKCS5Padding";

    /**
     * The constant SECRET_INSTANCE
     *
     * @author [오지훈]
     */
    private final static String SECRET_INSTANCE = "PBKDF2WithHmacSHA1";

    /**
     * The constant SECRET_SPEC
     *
     * @author [오지훈]
     */
    private final static String SECRET_SPEC = "AES";

    /**
     * The constant BASIC_ENCODE
     *
     * @author [오지훈]
     */
    private final static String BASIC_ENCODE = "UTF-8";

    /**
     * Encrypt aes 256 string.
     *
     * @param msg the msg
     * @param key the key
     * @return the string
     * @author [오지훈]
     * @implNote AES256 암호화
     * @since 2020. 6. 22. 오후 4:08:32
     */
    public String encryptAES256(final String msg, final String key) {
        try {
            final SecureRandom random = new SecureRandom();
            final byte[] _bytes = new byte[20];
            random.nextBytes(_bytes);

            final SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_INSTANCE);
            final PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), _bytes, HASH_CYCLE, HASH_BIT); // 70000번 해시하여 256 bit 길이의 키를 만든다.
            final SecretKey secretKey = factory.generateSecret(spec);
            final SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), SECRET_SPEC);
            final Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            cipher.init(Cipher.ENCRYPT_MODE, secret);

            final AlgorithmParameters params = cipher.getParameters();
            final byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
            final byte[] encryptionTextBytes = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
            final byte[] buffer = new byte[_bytes.length + ivBytes.length + encryptionTextBytes.length];

            System.arraycopy(_bytes, 0, buffer, 0, _bytes.length);
            System.arraycopy(ivBytes, 0, buffer, _bytes.length, ivBytes.length);
            System.arraycopy(encryptionTextBytes, 0, buffer, _bytes.length + ivBytes.length, encryptionTextBytes.length);
            return Base64.getEncoder().encodeToString(buffer);
        } catch (Exception exception) {
            return FailCode.ExceptionError.ERROR.name();
        }
    }

    /**
     * Decrypt aes 256 string.
     *
     * @param msg the msg
     * @param key the key
     * @return the string
     * @author [오지훈]
     * @implNote AES256 복호화
     * @since 2020. 6. 22. 오후 4:08:32
     */
    public String decryptAES256(final String msg, final String key) {
        try {
            final Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            final ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(msg));

            final byte[] _bytes = new byte[20];
            buffer.get(_bytes, 0, _bytes.length);

            final byte[] ivBytes = new byte[cipher.getBlockSize()];
            buffer.get(ivBytes, 0, ivBytes.length);

            final byte[] encryoptionTextBytes = new byte[buffer.capacity() - _bytes.length - ivBytes.length];
            buffer.get(encryoptionTextBytes);

            final SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_INSTANCE);
            final PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), _bytes, HASH_CYCLE, HASH_BIT);
            cipher.init(Cipher.DECRYPT_MODE
                    , new SecretKeySpec(factory.generateSecret(spec).getEncoded(), SECRET_SPEC)
                    , new IvParameterSpec(ivBytes));
            return new String(cipher.doFinal(encryoptionTextBytes));
        } catch (Exception exception) {
            return FailCode.ExceptionError.ERROR.name();
        }
    }

    /**
     * Url encode string.
     *
     * @param value the value
     * @return the string
     * @author [오지훈]
     * @implNote Url 암호화
     * @since 2020. 6. 22. 오후 4:08:32
     */
    public String urlEncode(final String value)  {
        try {
            return URLEncoder.encode(value, BASIC_ENCODE);
        } catch (UnsupportedEncodingException exception) {
            return FailCode.ExceptionError.ERROR.name();
        }
    }

    /**
     * Url decode string.
     *
     * @param value the value
     * @return the string
     * @author [오지훈]
     * @implNote Url 복호화
     * @since 2020. 6. 22. 오후 4:08:32
     */
    public String urlDecode(final String value) {
        try {
            return URLDecoder.decode(value, BASIC_ENCODE);
        } catch (UnsupportedEncodingException exception) {
            return FailCode.ExceptionError.ERROR.name();
        }
    }
}
