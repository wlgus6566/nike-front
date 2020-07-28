package com.nike.dnp.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.nike.dnp.dto.file.FileResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.Date;


/**
 * S3 Util
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 27. 오후 4:09:51
 * @Description
 */
@Slf4j
@Component
public class S3Util {


	/**
	 * The constant root
	 *
	 * @author [윤태호]
	 */
	private static String root;


	/**
	 * The constant accessKey
	 *
	 * @author [윤태호]
	 */
	private static String accessKey;


	/**
	 * The constant secretKey
	 *
	 * @author [윤태호]
	 */
	private static String secretKey;

	/**
	 * The constant bucket
	 *
	 * @author [윤태호]
	 */
	private static String bucket;

	/**
	 * The constant region
	 *
	 * @author [윤태호]
	 */
	private static String region;

	/**
	 * The constant client
	 *
	 * @author [윤태호]
	 */
	private static AmazonS3 client;


	/**
	 * Set root.
	 *
	 * @param root the root
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:09:51
	 * @Description
	 */
	@Value("${nike.file.root:}")
	public void setRoot(final String root){
		this.root = root;
	}

	/**
	 * Sets access key.
	 *
	 * @param accessKey the access key
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:09:51
	 * @Description
	 */
	@Value("${cloud.aws.credentials.accessKey:}")
	public void setAccessKey(final String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * Sets secret key.
	 *
	 * @param secretKey the secret key
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:09:51
	 * @Description
	 */
	@Value("${cloud.aws.credentials.secretKey:}")
	public void setSecretKey(final String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * Sets bucket.
	 *
	 * @param bucket the bucket
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:09:52
	 * @Description
	 */
	@Value("${cloud.aws.s3.bucket:}")
	public void setBucket(final String bucket) {
		this.bucket = bucket;
	}

	/**
	 * Sets region.
	 *
	 * @param region the region
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:09:52
	 * @Description
	 */
	@Value("${cloud.aws.region.static:}")
	public void setRegion(final String region) {
		this.region = region;
	}

	/**
	 * S3 기본 셋팅
	 *
	 * @return the amazon s 3
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:09:52
	 * @Description
	 */
	public static AmazonS3 init(){
		log.debug("S3 Init");
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion(region).build();
		return client;
	}

	/**
	 * S3 업로드
	 *
	 * @param fileResultDTO the file result dto
	 * @return the url
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:09:52
	 * @Description
	 */
	public static String upload(final FileResultDTO fileResultDTO) {
		File file = new File(root+ fileResultDTO.getFilePhysicalName());
		String uploadUrl = awsPathReplace(fileResultDTO.getFilePhysicalName());
		client.putObject(new PutObjectRequest(bucket, uploadUrl, file).withCannedAcl(CannedAccessControlList.PublicRead));
		URL url = client.getUrl(bucket, uploadUrl);
		log.debug("url.toString() {}", url.toString());
		return url.getPath();
	}

	/**
	 * S3 파일 복사
	 *
	 * @param oldFile       기존 파일
	 * @param newFolder     복사할 폴더
	 * @param oldFileDelete 기존 파일 삭제 유무
	 * @return the url
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:09:52
	 * @Description
	 */
	public static String fileCopy(final String oldFile, String newFolder,boolean oldFileDelete) {
		final String awsOldPath = awsPathReplace(oldFile);
		final String fileName = StringUtils.getFilename(awsOldPath);
		final String awsNewPath = newFolder+"/"+fileName;
		final CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucket, awsOldPath, bucket, awsNewPath).withCannedAccessControlList(CannedAccessControlList.PublicRead);
		client.copyObject(copyObjectRequest);
		final URL url = client.getUrl(bucket, awsNewPath);
		// 기존 파일 삭제
		if(oldFileDelete){
			client.deleteObject(bucket, awsOldPath);
		}
		log.debug("url.toString() {}", url.toString());
		return url.getPath();
	}


	/**
	 * S3 파일 복사 및 기존 파일 삭제
	 *
	 * @param oldFile   기존 파일
	 * @param newFolder 복사할 폴더
	 * @return the url
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:09:52
	 * @Description
	 */
	public static String fileCopyAndOldFileDelete(final String oldFile, final String newFolder){
		return fileCopy(oldFile, newFolder,true);
	}

	/**
	 * S3 파일 복사
	 *
	 * @param oldFile   기존 파일
	 * @param newFolder 복사할 폴더
	 * @return the url
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:11:49
	 * @Description
	 */
	public static String fileCopy(final String oldFile, final String newFolder) {
		return fileCopy(oldFile, newFolder, false);
	}

	/**
	 * 템프 파일 삭제 [등록후 하루 지난 파일 삭제]
	 *
	 * @param deleteFile the delete file
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:58:52
	 * @Description
	 */
	public static void tempFileDelete(final String deleteFile){
		final String awsFile = awsPathReplace(deleteFile);
		final S3Object object = client.getObject(bucket, awsFile);

		final Date lastModified = object.getObjectMetadata().getLastModified();
		//하루 더하기
		final Date checkDate = new Date(lastModified.getTime() + (24 * 60 * 60 * 1000));
		if(checkDate.before(new Date())){
			fileDelete(deleteFile);
		}
	}

	/**
	 * 파일 삭제
	 *
	 * @param deleteFile 삭제 버킷 경로
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:58:52
	 * @Description
	 */
	public static void fileDelete(final String deleteFile) {
		final String awsFile = awsPathReplace(deleteFile);
		client.deleteObject(bucket, awsFile);
	}


	/**
	 * Aws path replace string.
	 *
	 * @param oldPath the old path
	 * @return the string
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:09:53
	 * @Description
	 */
	private static String awsPathReplace(final String oldPath) {
		String awsPath = oldPath.replace(File.separator, "/");
		if(awsPath.indexOf('/') == 0){
			awsPath = awsPath.substring(awsPath.indexOf('/')+1);
		}
		return awsPath;
	}


}
