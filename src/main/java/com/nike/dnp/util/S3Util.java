package com.nike.dnp.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.file.FileResultDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;


/**
 * S3 Util
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 7. 27. 오후 4:09:51
 */
@Slf4j
@Component
@NoArgsConstructor
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
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:09:51
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
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:09:51
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
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:09:51
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
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:09:52
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
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:09:52
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
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:09:52
	 */
	public static void init(){
		log.debug("S3 Init");
		final AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.withRegion(region).build();

	}

	/**
	 * S3 업로드
	 *
	 * @param fileResultDTO the file result dto
	 * @return the url
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:09:52
	 */
	public static void upload(final FileResultDTO fileResultDTO) {
		log.info("S3Util.upload");
		final StopWatch stopWatch = new StopWatch("S3Util.upload");
		if(!ObjectUtils.isEmpty(fileResultDTO.getFilePhysicalName())){
			stopWatch.start("original upload");
			s3upload(fileResultDTO.getFilePhysicalName());
			stopWatch.stop();
			log.debug("stopWatch.getLastTaskTimeMillis()  {} : {} ms",stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());
		}
		if(!ObjectUtils.isEmpty(fileResultDTO.getThumbnailPhysicalName())){
			stopWatch.start("thumbnail upload");
			s3upload(fileResultDTO.getThumbnailPhysicalName());
			stopWatch.stop();
			log.debug("stopWatch.getLastTaskTimeMillis()  {} : {} ms", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());
		}
		if(!ObjectUtils.isEmpty(fileResultDTO.getDetailThumbnailPhysicalName())){
			stopWatch.start("detailThumbnail upload");
			s3upload(fileResultDTO.getDetailThumbnailPhysicalName());
			stopWatch.stop();
			log.debug("stopWatch.getLastTaskTimeMillis()  {} : {} ms", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());
		}
		log.debug("stopWatch.getTotalTimeSeconds()  s3upload : {} s", stopWatch.getTotalTimeSeconds());
		log.debug("stopWatch.shortSummary() {}", stopWatch.shortSummary());
		log.debug("stopWatch.prettyPrint() {}", stopWatch.prettyPrint());
	}

	/**
	 * s3 파일 업로드
	 *
	 * @param filePath the file path
	 * @return the string
	 * @author [윤태호]
	 * @since 2020. 7. 31. 오전 11:15:34
	 */
	private static void s3upload(final String filePath){
		log.info("S3Util.s3upload");
		final File file = new File(root + filePath);
		final String uploadUrl = awsPathReplace(filePath);
		client.putObject(new PutObjectRequest(bucket, uploadUrl, file).withCannedAcl(CannedAccessControlList.Private));
		final URL url = client.getUrl(bucket, uploadUrl);
		log.debug("url.getPath() {}", url.getPath());
	}

	/**
	 * S3 파일 복사
	 *
	 * @param oldFile       기존 파일
	 * @param newFolder     복사할 폴더
	 * @param oldFileDelete 기존 파일 삭제 유무
	 * @return the url
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:09:52
	 */
	public static String fileCopy(final String oldFile, final String newFolder,final boolean oldFileDelete) {
		log.info("S3Util.fileCopy");
		final String awsOldPath = awsPathReplace(oldFile);
		final String fileName = StringUtils.getFilename(awsOldPath);
		final String awsNewPath = newFolder+"/"+fileName;
		final CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucket, awsOldPath, bucket, awsNewPath).withCannedAccessControlList(CannedAccessControlList.Private);
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
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:09:52
	 */
	public static String fileCopyAndOldFileDelete(final String oldFile, final String newFolder) {
		log.info("S3Util.fileCopyAndOldFileDelete");
		String result = oldFile;
		if (oldFile.contains(ServiceCode.FileFolderEnumCode.TEMP.getFolder())) {
			result = fileCopy(oldFile, newFolder,true);
		}
		return result;
	}

	/**
	 * S3 파일 복사
	 *
	 * @param oldFile   기존 파일
	 * @param newFolder 복사할 폴더
	 * @return the url
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:11:49
	 */
	public static String fileCopy(final String oldFile, final String newFolder) {
		log.info("S3Util.fileCopy");
		return fileCopy(oldFile, newFolder, false);
	}

	/**
	 * 템프 파일 삭제 [등록후 하루 지난 파일 삭제]
	 *
	 * @param deleteFile the delete file
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:58:52
	 */
	public static void tempFileDelete(final String deleteFile){
		log.info("S3Util.tempFileDelete");
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
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:58:52
	 */
	public static void fileDelete(final String deleteFile) {
		log.info("S3Util.fileDelete");
		final String awsFile = awsPathReplace(deleteFile);
		client.deleteObject(bucket, awsFile);
	}

	/**
	 * S3ObjectInputStream 조회
	 *
	 * @param path the path
	 * @return the file
	 * @author [윤태호]
	 * @since 2020. 7. 28. 오후 2:18:36
	 */
	public static S3ObjectInputStream getFile(final String path) {
		log.info("S3Util.getFile");
		final String awsPath = awsPathReplace(path);
		final S3Object object = client.getObject(bucket, awsPath);
		return object.getObjectContent();
	}


	/**
	 * multipartFile 을 s3 에 저장
	 *
	 * @param multipartFile MutipartFile
	 * @param folder        the folder
	 * @return the string
	 * @throws IOException the io exception
	 * @author [윤태호]
	 * @since 2020. 7. 29. 오후 2:03:53
	 */
	public static String upload(final MultipartFile multipartFile,final String folder) throws IOException {
		log.info("S3Util.upload");
		final String ext = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
		final String awsPath =folder+"/"+FileUtil.makeFileName()+"."+ext;
		final ObjectMetadata objectMetadata = new ObjectMetadata();
		client.putObject(new PutObjectRequest(bucket,awsPath,multipartFile.getInputStream(),objectMetadata).withCannedAcl(CannedAccessControlList.Private));
		final URL url = client.getUrl(bucket, awsPath);
		log.debug("url.toString() {}", url.toString());
		return url.getPath();
	}


	/**
	 * 아마존 패스 경로 수정
	 *
	 * @param oldPath the old path
	 * @return the string
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:09:53
	 */
	public static String awsPathReplace(final String oldPath) {
		log.info("S3Util.awsPathReplace");
		String awsPath = oldPath.replace(File.separator, "/");
		if(awsPath.indexOf('/') == 0){
			awsPath = awsPath.substring(awsPath.indexOf('/')+1);
		}
		return awsPath;
	}

	/**
	 * Generate presigned url.
	 *
	 * @param objectKey the object key
	 * @author [오지훈]
	 * @implNote S3 Signed URL 적용
	 * @since 2020. 8. 10. 오전 9:42:48
	 */
	public static void GeneratePresignedURL(final String objectKey) {
		String bucketName = "nike-test-bucket-dnp";
		try {
			// Set the presigned URL to expire after one hour.
			java.util.Date expiration = new java.util.Date();
			long expTimeMillis = expiration.getTime();
			expTimeMillis += 1000 * 60;
//			expTimeMillis += 1000 * 60 * 60;
			expiration.setTime(expTimeMillis);
			// Generate the presigned URL.
			System.out.println("Generating pre-signed URL.");

			GeneratePresignedUrlRequest generatePresignedUrlRequest =
					new GeneratePresignedUrlRequest(bucketName, objectKey)
							.withMethod(HttpMethod.GET)
							.withExpiration(expiration);
			URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
			System.out.println("Pre-Signed URL: " + url.toString());
		} catch (AmazonServiceException e) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			e.printStackTrace();
		} catch (SdkClientException e) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			e.printStackTrace();
		}
	}

}
