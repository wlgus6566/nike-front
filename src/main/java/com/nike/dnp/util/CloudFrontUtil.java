package com.nike.dnp.util;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.exception.CodeMessageHandleException;
import lombok.extern.slf4j.Slf4j;
import org.jets3t.service.CloudFrontService;
import org.jets3t.service.CloudFrontServiceException;
import org.jets3t.service.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Security;
import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * The type Cloud front util.
 *
 * @author [오지훈]
 * @implNote CloudFront Signed URL 생성 UTIL
 * @since 2020. 8. 7. 오후 4:32:56
 */
@Slf4j
@Component
public class CloudFrontUtil {

    /**
     * The BasicMinute
     *
     * @author [오지훈]
     */
    private final static Integer BASIC_MINUTE = 1;

    /**
     * The DistributionDomain
     *
     * @author [오지훈]
     */
    private static String DISTRIBUTION_DOMAIN;

    /**
     * The PrivateKeyFilePath
     *
     * @author [오지훈]
     */
    private static String PRIVATE_KEY_FILE_PATH;

    /**
     * The KeyPairId
     *
     * @author [오지훈]
     */
    private static String KEY_PAIR_ID;

    /**
     * Sets distribution domain.
     *
     * @param distributionDomain the distribution domain
     * @author [오지훈]
     * @implNote DISTRIBUTION_DOMAIN Setter
     * @since 2020. 8. 7. 오후 4:32:56
     */
    @Value("${cloud.aws.distributionDomain}")
    public void setDistributionDomain(String distributionDomain) {
        DISTRIBUTION_DOMAIN = distributionDomain;
    }

    /**
     * Sets private key file path.
     *
     * @param privateKeyFilePath the private key file path
     * @author [오지훈]
     * @implNote PRIVATE_KEY_FILE_PATH Setter
     * @since 2020. 8. 7. 오후 4:32:56
     */
    @Value("${cloud.aws.privateKeyFilePath}")
    public void setPrivateKeyFilePath(String privateKeyFilePath) {
        PRIVATE_KEY_FILE_PATH = privateKeyFilePath;
    }

    /**
     * Sets key pair id.
     *
     * @param keyPairId the key pair id
     * @author [오지훈]
     * @implNote KEY_PAIR_ID Setter
     * @since 2020. 8. 7. 오후 4:32:56
     */
    @Value("${cloud.aws.keyPairId}")
    public void setKeyPairId(String keyPairId) {
        KEY_PAIR_ID = keyPairId;
    }

    /**
     * Gets signed url.
     *
     * @param objectKey the object key
     * @param minute    the minute
     * @return the signed url
     * @author [오지훈]
     * @implNote AWS 기본 정책 URL
     * @since 2020. 8. 7. 오후 4:32:56
     */
    public static String getSignedUrl(final String objectKey, final int minute) {
        return createSignedUrlCanned(objectKey, minute);
    }

    /**
     * Gets signed url.
     *
     * @param objectKey the object key
     * @return the signed url
     * @author [오지훈]
     * @implNote AWS 기본 정책 URL
     * @since 2020. 8. 7. 오후 4:32:56
     */
    public static String getSignedUrl(final String objectKey) {
        return createSignedUrlCanned(objectKey, BASIC_MINUTE);
    }

    /**
     * Gets custom signed url.
     *
     * @param objectKey the object key
     * @param minute    the minute
     * @return the custom signed url
     * @author [오지훈]
     * @implNote 사용자 지정 정책 URL
     * @since 2020. 8. 7. 오후 4:32:56
     */
    public static String getCustomSignedUrl(final String objectKey, final int minute) {
        return createCustomSingedUrl(objectKey, minute);
    }

    /**
     * Gets custom signed url.
     *
     * @param objectKey the object key
     * @return the custom signed url
     * @author [오지훈]
     * @implNote 사용자 지정 정책 URL
     * @since 2020. 8. 7. 오후 4:32:56
     */
    public static String getCustomSignedUrl(final String objectKey) {
        return createCustomSingedUrl(objectKey, BASIC_MINUTE);
    }

    /**
     * Create signed url canned string.
     *
     * @param objectKey the object key
     * @param minute    the minute
     * @return the string
     * @author [오지훈]
     * @implNote AWS 기본 정책 URL 생성
     * @since 2020. 8. 7. 오후 3:07:14
     */
    public static String createSignedUrlCanned(final String objectKey, final int minute) {
        final String newObjectKey = S3Util.awsPathReplace(objectKey);

        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            final String policyResourcePath = "https://" + DISTRIBUTION_DOMAIN + "/" + newObjectKey;
            //final byte[] derPrivateKey = ServiceUtils.readInputStreamToBytes(new FileInputStream(PRIVATE_KEY_FILE_PATH));
            final byte[] derPrivateKey = ServiceUtils.readInputStreamToBytes(Files.newInputStream(Paths.get(PRIVATE_KEY_FILE_PATH)));

            return CloudFrontService.signUrlCanned(
                    policyResourcePath, // Resource URL or Path
                    KEY_PAIR_ID,     // Certificate identifier,
                    derPrivateKey, // DER Private key data
                    ServiceUtils.parseIso8601Date(LocalDateTime.now().plusMinutes(minute).toString()) // DateLessThan
            );
        } catch (CloudFrontServiceException | ParseException | IOException exception) {
            log.error("exception", exception);
            throw new CodeMessageHandleException(
                    FailCode.ExceptionError.ERROR.name()
                    , exception.getMessage()
            );
        }
    }

    /**
     * Create custom singed url string.
     *
     * @param objectKey the object key
     * @param minute    the minute
     * @return the string
     * @author [오지훈]
     * @implNote 사용자 지정 정책 URL 생성
     * @since 2020. 8. 7. 오후 4:32:57
     */
    public static String createCustomSingedUrl(final String objectKey, final int minute) {
        final String newObjectKey = S3Util.awsPathReplace(objectKey);

        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            final String policyResourcePath = "https://" + DISTRIBUTION_DOMAIN + "/" +  newObjectKey;
            //final byte[] derPrivateKey = ServiceUtils.readInputStreamToBytes(new FileInputStream(PRIVATE_KEY_FILE_PATH));
            final byte[] derPrivateKey = ServiceUtils.readInputStreamToBytes(Files.newInputStream(Paths.get(PRIVATE_KEY_FILE_PATH)));

            final String policy = CloudFrontService.buildPolicyForSignedUrl(
                    // Resource path (optional, can include '*' and '?' wildcards)
                    policyResourcePath,
                    // DateLessThan 종료, 접근 만료시간 세팅
                    ServiceUtils.parseIso8601Date(LocalDateTime.now().plusMinutes(minute).toString()),
                    // CIDR IP address restriction (optional, 0.0.0.0/0 means everyone)
                    "0.0.0.0/0",
                    // DateGreaterThan (optional) 시작
                    ServiceUtils.parseIso8601Date(LocalDateTime.now().toString())
            );

            return CloudFrontService.signUrl(
                    // Resource URL or Path
                    policyResourcePath,
                    // Certificate identifier, an active trusted signer for the distribution
                    KEY_PAIR_ID,
                    // DER Private key data
                    derPrivateKey,
                    // Access control policy
                    policy
            );
        } catch (CloudFrontServiceException | ParseException | IOException exception) {
            throw new CodeMessageHandleException(
                    FailCode.ExceptionError.ERROR.name()
                    , exception.getMessage()
            );
        }
    }

//    public void getCloudFrontCookieForCannedPolicy() throws ParseException, InvalidKeySpecException, IOException {
//
//        CloudFrontCookieSigner.CookiesForCannedPolicy cookies = CloudFrontCookieSigner.getCookiesForCannedPolicy(
//                SignerUtils.Protocol.http, distributionDomain, new File(privateKeyFilePath), s3ObjectKey,
//                keyPairId,  ServiceUtils.parseIso8601Date("2020-08-06T17:30:00.000Z"));
//        // 아래 세개의 값을 세팅한다
//        /*
//        httpGet.addHeader("Cookie", cookiesForCannedPolicy.getExpires().getKey() + "=" +
//                cookies.getExpires().getValue());
//        httpGet.addHeader("Cookie", cookiesForCannedPolicy.getSignature().getKey() + "=" +
//                cookies.getSignature().getValue());
//        httpGet.addHeader("Cookie", cookiesForCannedPolicy.getKeyPairId().getKey() + "=" +
//                cookies.getKeyPairId().getValue());
//        */
//    }

}
