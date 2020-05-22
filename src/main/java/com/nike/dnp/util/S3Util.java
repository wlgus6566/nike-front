package com.nike.dnp.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * S3Util
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description S3Util 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

public class S3Util {

    private AmazonS3 conn;
    private String bucketName = "emotion-cola";
    private String accessKey = "AKIA2Q5ZJWKPSHEHO4ZP";
    private String secretKey = "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";

    /**
     * Amazon S3 Connect
     */
    public S3Util() {
        AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
        conn = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(creds)).withRegion(Regions.AP_SOUTHEAST_1) // region
                .withForceGlobalBucketAccessEnabled(true) // access
                .build();
    }

    /**
     * Amazon S3 file Upload
     * 
     * @param fileName
     * @param fileData
     * @throws FileNotFoundException
     */
    public void fileUpload(String fileName, byte[] fileData) throws FileNotFoundException {
        String filePath = (fileName).replace(File.separatorChar, '/'); // 파일 구별자를 `/`로 설정(\->/) 이게 기존에 / 였어도 넘어오면서 \로 바뀌는 거같다.
        ObjectMetadata metaData = new ObjectMetadata();
        metaData.setContentLength(fileData.length); // 메타데이터 설정 -->원래는 128kB까지 업로드 가능했으나 파일크기만큼 버퍼를 설정시켰다.
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData); // 파일 넣음

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, byteArrayInputStream, metaData);
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

        conn.putObject(putObjectRequest);
    }

    /**
     * Amazon S3 file Delete
     * 
     * @param fileNames
     */
    public void fileDelete(String fileNames) {
        String filePath = fileNames.substring(0, fileNames.lastIndexOf("/"));
        String fileName = fileNames.substring(fileNames.lastIndexOf("/") + 1);

        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName + filePath, fileName);
        conn.deleteObject(deleteObjectRequest);
    }

    /**
     * Amazon S3 File info
     * 
     * @param fileName
     * @return
     */
    public String getFileURL(String fileName) {
        String imgName = (fileName).replace(File.separatorChar, '/');
        return conn.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, imgName)).toString();
    }

}
