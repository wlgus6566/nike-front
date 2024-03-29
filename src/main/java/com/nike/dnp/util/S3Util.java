package com.nike.dnp.util;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.file.FileResultDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
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
	 * The constant bucket
	 *
	 * @author [윤태호]
	 */
	private static String bucket;

	/**
	 * The constant profile
	 *
	 * @author [윤태호]
	 */
	private static String profile;

	/**
	 * The constant client
	 *
	 * @author [윤태호]
	 */
	private static AmazonS3 client;

	/**
	 * The constant editorBucket
	 *
	 * @author [정주희]
	 */
	private static String editorBucket;

	/**
	 * Set root.
	 *
	 * @param root the root
	 * @author [윤태호]
	 * @implNote 설명] 설명]
	 * @since 2020. 7. 27. 오후 4:09:51
	 */
	@Value("${nike.file.root:}")
	public void setRoot(final String root){
		this.root = root;
	}

	/**
	 * Sets bucket.
	 *
	 * @param bucket the bucket
	 * @author [윤태호]
	 * @implNote 설명] 설명]
	 * @since 2020. 7. 27. 오후 4:09:52
	 */
	@Value("${cloud.aws.s3.bucket:}")
	public void setBucket(final String bucket) {
		this.bucket = bucket;
	}

	/**
	 * Sets editor bucket.
	 *
	 * @param editorBucket the editor bucket
	 * @author [정주희]
	 * @implNote 에디터 이미지 업로드 버켓
	 * @since 2020. 8. 19. 오후 2:53:37
	 */
	@Value("${cloud.aws.s3.editor:}")
	public void setEditorBucket(final String editorBucket) {
		this.editorBucket = editorBucket;
	}

	/**
	 * S3 기본 셋팅
	 *
	 * @return the amazon s 3
	 * @author [윤태호]
	 * @implNote 설명] 설명]
	 * @since 2020. 7. 27. 오후 4:09:52
	 */
	public static void init(){
		log.debug("S3 Init");
		log.debug("profile : " + System.getProperty("spring.profiles.active"));
		client = AmazonS3ClientBuilder.standard()
				//.withRegion("ap-northeast-2")
				.withCredentials(new ProfileCredentialsProvider(System.getProperty("spring.profiles.active")))
				.build();
	}

	/**
	 * S3 업로드
	 *
	 * @param fileResultDTO the file result dto
	 * @param privateYn     the private yn
	 * @param downloadYn    the download yn
	 * @return the url
	 * @author [윤태호]
	 * @implNote 설명] 설명]
	 * @since 2020. 7. 27. 오후 4:09:52
	 */
	public static void upload(final FileResultDTO fileResultDTO, final String privateYn, final String downloadYn) {
		log.info("S3Util.upload");
		final StopWatch stopWatch = new StopWatch("S3Util.upload");
		if(!ObjectUtils.isEmpty(fileResultDTO.getFilePhysicalName())){
			stopWatch.start("original upload");
			s3upload(fileResultDTO.getFilePhysicalName(), privateYn, downloadYn);
			stopWatch.stop();
			log.debug("stopWatch.getLastTaskTimeMillis()  {} : {} ms",stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());
		}

		//TODO[ojh] 2020/09/18 : 동영상 이슈 해결 후 조건 제거 예정
		if (!ObjectUtils.isEmpty(fileResultDTO.getFileContentType())
				&& fileResultDTO.getFileContentType().contains("VIDEO")) {}
		else {
			if(!ObjectUtils.isEmpty(fileResultDTO.getThumbnailFilePhysicalName())){
				stopWatch.start("thumbnail upload");
				s3upload(fileResultDTO.getThumbnailFilePhysicalName(), privateYn, downloadYn);
				stopWatch.stop();
				log.debug("stopWatch.getLastTaskTimeMillis()  {} : {} ms", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());
			}
			if(!ObjectUtils.isEmpty(fileResultDTO.getDetailThumbnailFilePhysicalName())){
				stopWatch.start("detailThumbnail upload");
				s3upload(fileResultDTO.getDetailThumbnailFilePhysicalName(), privateYn, downloadYn);
				stopWatch.stop();
				log.debug("stopWatch.getLastTaskTimeMillis()  {} : {} ms", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());
			}
		}
		log.debug("stopWatch.getTotalTimeSeconds()  s3upload : {} s", stopWatch.getTotalTimeSeconds());
		log.debug("stopWatch.shortSummary() {}", stopWatch.shortSummary());
		log.debug("stopWatch.prettyPrint() {}", stopWatch.prettyPrint());
	}

	/**
	 * s3 파일 업로드
	 *
	 * @param filePath   the file path
	 * @param privateYn  the private yn
	 * @param downloadYn the download yn
	 * @return the string
	 * @author [윤태호]
	 * @implNote [method 설명]
	 * @since 2020. 7. 31. 오전 11:15:34
	 */
	private static void s3upload(final String filePath, final String privateYn, final String downloadYn){
		log.info("S3Util.s3upload");
		final File file = new File(root + filePath);
		final String uploadUrl = awsPathReplace(filePath);
		final PutObjectRequest putObjectRequest;
		final ObjectMetadata metadata = new ObjectMetadata();
		if (privateYn.equals("Y")) {
			putObjectRequest = new PutObjectRequest(bucket, uploadUrl, file).withCannedAcl(CannedAccessControlList.Private);
		} else {
			putObjectRequest = new PutObjectRequest(editorBucket, uploadUrl, file).withCannedAcl(CannedAccessControlList.PublicReadWrite);
		}

		if (downloadYn.equals("Y")) {
			metadata.setContentDisposition("attachment;");
		} else {
			metadata.setContentDisposition("inline");
		}
		putObjectRequest.setMetadata(metadata);
		client.putObject(putObjectRequest);
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
	 * @implNote 설명] 설명]
	 * @since 2020. 7. 27. 오후 4:09:52
	 */
	public static String fileCopy(final String oldFile, final String newFolder,final boolean oldFileDelete, final boolean privateBucket) {
		log.info("S3Util.fileCopy");


		final String awsOldPath = awsPathReplace(oldFile);
		final String fileName = StringUtils.getFilename(awsOldPath);
		final String awsNewPath = newFolder+"/"+fileName;

		String bucketPath = bucket;
		if (!privateBucket) {
			bucketPath = editorBucket;
		}

		final CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucketPath, awsOldPath, bucketPath, awsNewPath)
				.withCannedAccessControlList(!privateBucket ? CannedAccessControlList.PublicReadWrite : CannedAccessControlList.Private);

//		final CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucketPath, awsOldPath, bucketPath, awsNewPath);
		client.copyObject(copyObjectRequest);
		final URL url = client.getUrl(bucketPath, awsNewPath);
		// 기존 파일 삭제
		if(privateBucket && oldFileDelete){
			client.deleteObject(bucketPath, awsOldPath);
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
	 * @implNote 설명] 설명]
	 * @since 2020. 7. 27. 오후 4:09:52
	 */
	public static String fileCopyAndOldFileDelete(final String oldFile, final String newFolder, final boolean privateBucket) {
		log.info("S3Util.fileCopyAndOldFileDelete");
		String result = oldFile;
		if (oldFile.contains(ServiceCode.FileFolderEnumCode.TEMP.getFolder())) {
			result = fileCopy(oldFile, newFolder,true, privateBucket);
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
	 * @implNote 설명] 설명]
	 * @since 2020. 7. 27. 오후 4:11:49
	 */
	public static String fileCopy(final String oldFile, final String newFolder) {
		log.info("S3Util.fileCopy");
		return fileCopy(oldFile, newFolder, false, true);
	}

	/**
	 * 템프 파일 삭제 [등록후 하루 지난 파일 삭제]
	 *
	 * @param deleteFile the delete file
	 * @author [윤태호]
	 * @implNote 설명] 설명]
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
	 * @implNote 설명] 설명]
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
	 * @implNote [method 설명]
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
	 * @implNote [method 설명]
	 * @since 2020. 7. 29. 오후 2:03:53
	 */
	public static String upload(final MultipartFile multipartFile,final String folder) throws IOException {
		log.info("S3Util.upload");
		final String ext = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
		final String awsPath =folder+"/"+FileUtil.makeFileName()+"."+ext;
		final ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(multipartFile.getBytes().length);
		client.putObject(new PutObjectRequest(bucket,awsPath,multipartFile.getInputStream(),objectMetadata).withCannedAcl(CannedAccessControlList.Private));
		final URL url = client.getUrl(bucket, awsPath);
		log.debug("url.toString() {}", url.toString());
		return url.getPath();
	}

	/**
	 * Editor upload string.
	 *
	 * @param multipartFile the multipart file
	 * @param awsPath       the aws path
	 * @return the string
	 * @throws IOException the io exception
	 * @author [정주희]
	 * @implNote [method 설명]
	 * @since 2020. 8. 19. 오후 2:52:50
	 */
	public static String editorUpload(final MultipartFile multipartFile, final String awsPath) throws IOException {
		log.info("S3Util.editorUpload");

		final ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(multipartFile.getBytes().length);
		client.putObject(new PutObjectRequest(editorBucket, awsPath, multipartFile.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
		final URL url = client.getUrl(editorBucket, awsPath);

		log.debug("url.toString() {}", url.toString());
		return url.getPath();
	}

	/**
	 * 아마존 패스 경로 수정
	 *
	 * @param oldPath the old path
	 * @return the string
	 * @author [윤태호]
	 * @implNote 설명] 설명]
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

}
