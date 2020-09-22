package com.nike.dnp.util;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.exception.FileHandleException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * FileUtil
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 7. 10. 오후 2:39:36
 */
@Component
@Slf4j
@NoArgsConstructor
public class FileUtil {

	/**
	 * The constant root
	 *
	 * @author [윤태호]
	 */
	private static String root;

	/**
	 * The constant imageMagick
	 *
	 * @author [윤태호]
	 */
	private static String imageMagick;

	/**
	 * The constant imageMagickCommand
	 *
	 * @author [김형욱]
	 */
	private static String imageMagickCommand;


	/**
	 * The constant ffmpeg
	 *
	 * @author [윤태호]
	 */
	private static String ffmpeg;


	/**
	 * The constant ffmpegCommand
	 *
	 * @author [윤태호]
	 */
	private static String ffmpegCommand;


	/**
	 * 파일 저장 경로
	 *
	 * @param root the root
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 10. 오후 2:39:36
	 */
	@Value("${nike.file.root:}")
	public void setRoot(final String root){
		this.root = root;
	}

	/**
	 * Set image magick.
	 *
	 * @param imageMagick the image magick
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 13. 오후 2:30:09
	 */
	@Value("${nike.file.imageMagick:}")
	public void setImageMagick(final String imageMagick){
		this.imageMagick = imageMagick;
	}

	/**
	 * Set image magick command.
	 *
	 * @param imageMagickCommand the image magick command
	 * @author [김형욱]
	 * @implNote
	 * @since 2020. 7. 17. 오전 11:55:43
	 */
	@Value("${nike.file.imageMagickCommand:}")
	public void setImageMagickCommand(final String imageMagickCommand){
		this.imageMagickCommand = imageMagickCommand;
	}

	/**
	 * Sets ffmpeg.
	 *
	 * @param ffmpeg the ffmpeg
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:58:36
	 */
	@Value("${nike.file.ffmpeg:}")
	public void setFfmpeg(final String ffmpeg) {
		this.ffmpeg = ffmpeg;
	}

	/**
	 * Sets ffmpeg command.
	 *
	 * @param ffmpegCommand the ffmpeg command
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:58:36
	 */
	@Value("${nike.file.ffmpegCommand:}")
	public void setFfmpegCommand(final String ffmpegCommand) {
		this.ffmpegCommand = ffmpegCommand;
	}

	/**
	 * 새로운 파일 생성
	 *
	 * @param folder    파일 폴더
	 * @param extension 파일 확장자
	 * @return the file
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 10. 오후 2:39:36
	 */
	public static File makeNewFile(final String folder,final String extension) {
		log.info("FileUtil.makeNewFile");
		String folderParam = whiteFolderSelect(folder);
		// [공백 폴더명] 권한 없음 처리
		if (ObjectUtils.isEmpty(folderParam)) {
			throw new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_FILE.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
			);
		} else {
			folderParam = cleanXSS(folderParam,false);
		}

		// [허용 가능 목록에 없는 폴더명 / 공백 폴더명] 권한 없음 처리
		if (!whiteFolderList(folderParam) ) {
			throw new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_FILE.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
			);
		}

		final File path = Paths.get(root, folderParam).toFile();
		log.debug(" makeNewFile root > {}", root);
		log.debug(" makeNewFile root > {}", folderParam);
		if (!path.exists()) {
			boolean mkdirs = path.mkdirs();
			log.debug("경로 생성");
		}
		final File result = Paths.get(root, folderParam, cleanXSS(makeFileName(), false) + "." + cleanXSS(extension, false)).toFile();
		log.debug("result.toString() > {}", result.toString());
		return result;


		/*
		final String newFilepath = root + File.separator + folderParam;
		final File result = new File(cleanXSS(newFilepath, false) + File.separator + cleanXSS(makeFileName(), false) + "." + cleanXSS(extension, false));
		new File(newFilepath).mkdirs();
		return result;
		*/
	}

	/**
	 * 파일명 생성
	 *
	 * @return the string
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 29. 오후 2:02:25
	 */
	public static String makeFileName(){
		log.info("FileUtil.makeFileName");
		return LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE) + LocalDateTime.now().get(ChronoField.MICRO_OF_SECOND) + RandomStringUtils
				.random(10, true, true);
	}

	/**
	 * 파일 저장
	 *
	 * @param uploadFile the upload file
	 * @param folder     the folder
	 * @param resize     the resize
	 * @param resizeExt  the resize ext
	 * @return the file result dto
	 * @throws IOException the io exception
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 13. 오후 4:55:25
	 */
	public static FileResultDTO fileSave(final MultipartFile uploadFile,
										 final String folder,
										 final boolean resize,
										 final String resizeExt) throws IOException {
		log.info("FileUtil.fileSave  start");

		System.out.println("======================================================");
		System.out.println("folder : " + folder);
		System.out.println("resize : " + resize);
		System.out.println("resizeExt : " + resizeExt);
		System.out.println("======================================================");


		String originalFileName = uploadFile.getOriginalFilename();
		// [공백 originalFileName] 권한 없음 처리
		if (ObjectUtils.isEmpty(originalFileName)) {
			throw new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_FILE.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
			);
//			throw new FileHandleException();
		} else {
			originalFileName = cleanXSS(originalFileName, false);
		}

		String folderParam = folder;
		// [공백 폴더명] 권한 없음 처리
		if (ObjectUtils.isEmpty(folderParam)) {
			throw new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_FILE.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
			);
//			throw new FileHandleException();
		} else {
			folderParam = cleanXSS(folderParam,false);
		}

		// [허용 가능 목록에 없는 폴더명 / 공백 폴더명] 권한 없음 처리
		if (!whiteFolderList(folderParam) ) {
//			throw new CodeMessageHandleException(
//					FailCode.ConfigureError.INVALID_FILE.name()
//					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
//			);
			throw new FileHandleException();
		}

		String contentType = uploadFile.getContentType();
		// [공백 ContentType] 권한 없음 처리
		if (ObjectUtils.isEmpty(contentType)) {
//			throw new CodeMessageHandleException(
//					FailCode.ConfigureError.INVALID_FILE.name()
//					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
//			);
			throw new FileHandleException();
		} else {
			contentType = contentType.toUpperCase(Locale.getDefault());
		}

		String extension = StringUtils.getFilenameExtension(originalFileName);
		// [허용 가능 목록에 없는 확장자 / 공백 확장자] 권한 없음 처리
		if (!whiteExtensionList(extension) || ObjectUtils.isEmpty(extension)) {
//			throw new CodeMessageHandleException(
//					FailCode.ConfigureError.INVALID_FILE.name()
//					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
//			);
			throw new FileHandleException();
		} else {
			extension = extension.toUpperCase(Locale.getDefault());
		}



		final File toFile = makeNewFile(folderParam, extension);
		log.debug("toFile.toString() > {}", toFile.toString());
		log.debug("uploadFile.toString() > {}", uploadFile.toString());
		uploadFile.transferTo(toFile);
		log.debug("파일 저장 완료");
		final FileResultDTO fileResultDTO = new FileResultDTO();
		fileResultDTO.setFileName(originalFileName);
		fileResultDTO.setFilePhysicalName(toFile.getCanonicalPath().replace(root, ""));
		fileResultDTO.setFileSize(toFile.length());
		fileResultDTO.setFileContentType(contentType);
		fileResultDTO.setFileExtension(extension);


		if (resize) {
			log.debug("파일 리사이즈 =========================================================");
			if (contentType.contains("IMAGE") || extension.contains("PSD") || extension.contains("AI")) {

				String resizeExtension = ObjectUtils.isEmpty(resizeExt) ? "JPG" : cleanXSS(resizeExt.toUpperCase(Locale.getDefault()), false);
				// [허용 가능 목록에 없는 확장자 / 공백 확장자] 권한 없음 처리
				if(!whiteExtensionList(resizeExtension)){
					throw new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
				}

				log.debug("toFile.getCanonicalPath() > {}", toFile.getCanonicalPath());
				final String detailPath = Paths.get(cleanXSS(StringUtils.stripFilenameExtension(toFile.getCanonicalPath()) + "_detail." + resizeExtension, true)).toString();
				log.debug("detailPath > {}", detailPath);
//				final String detailPath = cleanXSS(StringUtils.stripFilenameExtension(toFile.getCanonicalPath()) + "_detail." + resizeExtension, true);
				final StringBuilder detailCommand = new StringBuilder(imageMagick)
						.append(File.separator)
						.append(imageMagickCommand)
						.append(" ")
						.append(toFile.getCanonicalPath());

				if(extension.contains("PSD") || extension.contains("AI") || extension.contains("TIF") || extension.contains("GIF")){
					detailCommand.append("[0]");
				}
				detailCommand.append(" -resize 700x700 -background #f7f7f7 -gravity center -extent 700x700 ").append(detailPath);

				try{
					final String cmd = whiteListing(detailCommand.toString(), folder);
					if (ObjectUtils.isEmpty(cmd)) {
//						throw new CodeMessageHandleException(
//								FailCode.ConfigureError.INVALID_FILE.name()
//								, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
//						);
						throw new FileHandleException();
					}
					log.debug("cmd > {}", cmd);
					final Process procDetail = Runtime.getRuntime().exec(cmd);
					procDetail.waitFor();
				}catch(InterruptedException exception){
					log.error("exception", exception);
				}

				final File detailFile = Paths.get(detailPath).toFile();
//				final File detailFile = new File(detailPath);
				log.debug("detailFile.toString() > {}", detailFile.toString());
				if(detailFile.isFile()){
					String detailThumbnail = originalFileName;
					detailThumbnail = detailThumbnail.replace("." + StringUtils.getFilenameExtension(detailThumbnail), "") + "_detail." + resizeExtension;
					fileResultDTO.setDetailThumbnailFileName(detailThumbnail);
					fileResultDTO.setDetailThumbnailFilePhysicalName(detailFile.getCanonicalPath().replace(root, ""));
					fileResultDTO.setDetailThumbnailFileSize(detailFile.length());
				}

				// 이미지 사이즈 100x100으로 변환
				final String thumbnailPath = Paths.get(cleanXSS(StringUtils.stripFilenameExtension(toFile.getCanonicalPath()) + "_thumbnail." + resizeExtension, true)).toString();
				log.debug("thumbnailPath > {}", thumbnailPath);
//				final String thumbnailPath = cleanXSS(StringUtils.stripFilenameExtension(toFile.getCanonicalPath()) + "_thumbnail." + resizeExtension, true);
				final StringBuilder command = new StringBuilder(imageMagick)
						.append(File.separator)
						.append(imageMagickCommand)
						.append(" ")
						.append(detailFile.getCanonicalPath());
				if(extension.contains("PSD") || extension.contains("AI")){
					command.append("[0]");
				}
				command.append(" -resize 100x100 -background white -gravity center -extent 100x100 ").append(thumbnailPath);

				try{
					final String cmd = whiteListing(command.toString(), folder);
					if (ObjectUtils.isEmpty(cmd)) {
//						throw new CodeMessageHandleException(
//								FailCode.ConfigureError.INVALID_FILE.name()
//								, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
//						);
						throw new FileHandleException();
					}
					log.debug("cmd > {}", cmd);
					final Process proc = Runtime.getRuntime().exec(cmd);
					proc.waitFor();
				}catch(InterruptedException exception){
					log.error("exception", exception);
				}

				final File thumbnailFile = Paths.get(thumbnailPath).toFile();
//				final File thumbnailFile = new File(thumbnailPath);
				log.debug("thumbnailFile.toString() > {}", thumbnailFile.toString());
				if(thumbnailFile.isFile()){
					String thumbnail = originalFileName;
					thumbnail = thumbnail.replace("." + StringUtils.getFilenameExtension(thumbnail), "") + "_thumbnail." + resizeExtension;
					fileResultDTO.setThumbnailFileName(thumbnail);
					fileResultDTO.setThumbnailFilePhysicalName(thumbnailFile.getCanonicalPath().replace(root, ""));
					fileResultDTO.setThumbnailFileSize(thumbnailFile.length());
				}
			}
			else if (contentType.contains("VIDEO")) {
				//TODO[ojh] 2020/09/18 : 동영상 인코딩 issue, 추후 디벨롭 예정
				//fileResultDTO.setDetailThumbnailFileName(fileResultDTO.getFileName());
				//fileResultDTO.setDetailThumbnailFilePhysicalName(fileResultDTO.getFilePhysicalName());
				//fileResultDTO.setDetailThumbnailFileSize(fileResultDTO.getFileSize());

				/*
				// 사이즈 변환시 700:394 를 변경 하면 됨
				final String thumbnailPath = Paths.get(cleanXSS(StringUtils.stripFilenameExtension(toFile.getCanonicalPath()) + "_detail.MP4", true)).toString();
				//final String thumbnailPath = cleanXSS(StringUtils.stripFilenameExtension(toFile.getCanonicalPath()) + "_detail.mp4", true);
				log.debug("thumbnailPath > {}", thumbnailPath);

				final String[] command = {
						ffmpeg + File.separator + ffmpegCommand
						, "-y"
						, "-i"
						, toFile.getCanonicalPath()
						, "-codec:v"
						, "libx264"
						, "-x264-params"
						, "opencl = true"
						, "-preset"
						, "ultrafast"
						, "-crf"
						, "33"
						, "-movflags"정
						, "faststart"
						, "-tune"
						, "zerolatency"
						, "-vf"
						, "scale=700:394:force_original_aspect_ratio=decrease,pad=700:394:(ow-iw/2):(oh-ih)/2:white"
						, thumbnailPath
				};

				final List<String> cmd = whiteListing(folder, command);
				cmd.stream().forEach(s -> log.debug("cmd > {}", s));
				if (ObjectUtils.isEmpty(cmd)) {
					throw new CodeMessageHandleException(
							FailCode.ConfigureError.INVALID_FILE.name()
							, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
					);
				}

				final ProcessBuilder processBuilder = new ProcessBuilder(cmd);
				processBuilder.redirectErrorStream(true);
				Process process = null;
				try{
					process = processBuilder.start();
				}catch(Exception exception){
					process.destroy();
					log.error("exception", exception);
				}
				exhaustInputStream(process.getInputStream());
				try{
					process.waitFor();
				}catch(InterruptedException exception){
					process.destroy();
				}

				// 정상 종료가 되지 않았을 경우
				if(process.exitValue() != 0){
					throw new CodeMessageHandleException(
							FailCode.ConfigureError.INVALID_FILE.name()
							, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
					);
				}

				final File detailFile = Paths.get(thumbnailPath).toFile();
				//final File detailFile = new File(thumbnailPath);
				log.debug("detailFile > {}", detailFile);
				if(detailFile.isFile()){
					String detailThumbnail = originalFileName;
					detailThumbnail = detailThumbnail.replace("." + StringUtils.getFilenameExtension(detailThumbnail), "") + "_detail.mp4";
					fileResultDTO.setDetailThumbnailFileName(detailThumbnail);
					fileResultDTO.setDetailThumbnailFilePhysicalName(detailFile.getCanonicalPath().replace(root, ""));
					fileResultDTO.setDetailThumbnailFileSize(detailFile.length());
				}
				*/
			}
		}

		return fileResultDTO;
	}

	/**
	 * 파일을 temp에 저장 및 이미지 리사이즈
	 *
	 * @param uploadFile the upload file
	 * @return the file result dto
	 * @throws IOException the io exception
	 *
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 13. 오후 4:55:25
	 */
	public static FileResultDTO fileTempSaveAndImageResize(final MultipartFile uploadFile) throws IOException {
		log.info("FileUtil.fileTempSaveAndImageResize");
		return fileSave(uploadFile, ServiceCode.FileFolderEnumCode.TEMP.getFolder(), true, null);
	}

	/**
	 * 파일을 temp에 저장 및 이미지 리사이즈
	 *
	 * @param uploadFile the upload file
	 * @param resizeExt  리사이즈 확장 명
	 * @return the file result dto
	 * @throws IOException the io exception
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 13. 오후 4:55:25
	 */
	public static FileResultDTO fileTempSaveAndImageResize(final MultipartFile uploadFile,
														   final String resizeExt) throws IOException  {
		log.info("FileUtil.fileTempSaveAndImageResize");
		return fileSave(uploadFile, ServiceCode.FileFolderEnumCode.TEMP.getFolder(), true, resizeExt);
	}

	/**
	 * 파일 저장
	 *
	 * @param uploadFile the upload file
	 * @param folder     the folder
	 * @return the file result dto
	 * @throws IOException the io exception
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 13. 오후 4:55:25
	 */
	public static FileResultDTO fileSave(final MultipartFile uploadFile, final String folder) throws IOException {
		log.info("FileUtil.fileSave");
		String folderParam = whiteFolderSelect(folder);
		// [공백 폴더명] 권한 없음 처리
		if (ObjectUtils.isEmpty(folderParam)) {
			throw new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_FILE.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
			);
		} else {
			folderParam = cleanXSS(folderParam,false);
		}

		// [허용 가능 목록에 없는 폴더명 / 공백 폴더명] 권한 없음 처리
		if (!whiteFolderList(folderParam) ) {
			throw new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_FILE.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
			);
		}
		return fileSave(uploadFile, folderParam, false, null);
	}

	/**
	 * 파일 temp 폴더에 저장
	 *
	 * @param uploadFile the upload file
	 * @return the file result dto
	 * @throws IOException the io exception
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 13. 오후 4:55:25
	 */
	public static FileResultDTO fileTempSave(final MultipartFile uploadFile) throws IOException {
		log.info("FileUtil.fileTempSave");
		return fileSave(uploadFile, ServiceCode.FileFolderEnumCode.TEMP.getFolder());
	}


	/**
	 * temp 폴더 파일 삭제 (등록후 24시간 지난 파일들만 삭제 처리)
	 *
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 13. 오후 4:55:25
	 */
	public static void deleteTemp() throws IOException {
		log.info("FileUtil.deleteTemp");
		//final File tempFile = new File(root+File.separator+ ServiceCode.FileFolderEnumCode.TEMP.getFolder());
		final File tempFile = Paths.get(root, ServiceCode.FileFolderEnumCode.TEMP.getFolder()).toFile();
		final File[] files = tempFile.listFiles();

		for(final File file : files){
			LocalDateTime updateDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), TimeZone.getDefault().toZoneId());
			updateDate = updateDate.plusHours(24);
			final LocalDateTime today = LocalDateTime.now();
			if(updateDate.isBefore(today) && file.isFile()){
				final String awsDeleteFile = file.getCanonicalPath().replace(root, "");
				file.delete();
				try{
					S3Util.fileDelete(awsDeleteFile);
				}catch(Exception exception){
					// TODO [YTH] 아마존 파일 없음
					throw (CodeMessageHandleException) new CodeMessageHandleException(
							FailCode.ExceptionError.ERROR.name()
							, exception.getMessage()
					);
				}
			}
		}
	}

	/**
	 * 파일 다운로드
	 *
	 * @param filePath the file path
	 * @return the single result
	 * @author [이소정]
	 * @implNote
	 * @since 2020. 7. 16. 오후 6:15:26
	 */
	public static ResponseEntity<Resource> fileDownload(final String filePath) {
		log.info("FileUtil.fileDownload");
		final Path path = Paths.get(filePath);
		final HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + path.getFileName().toString());
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(path.toFile().length()));
		final Resource resource = new FileSystemResource(Paths.get(path.toUri()).toFile());
		//final Resource resource = new FileSystemResource(new File(path.toUri()));
		return new ResponseEntity<>(resource,headers, HttpStatus.OK);
	}


	/**
	 * S3 파일 다운로드
	 *
	 * @param path     다운로드 파일 경로
	 * @param fileName 다운로드 파일 명
	 * @return the response entity
	 * @throws IOException the io exception
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 28. 오후 2:19:30
	 */
	public static ResponseEntity<Resource> s3FileDownload(String path,final String fileName) throws IOException {
		log.info("FileUtil.s3FileDownload");
		System.out.println("======================================================");
		System.out.println("path : " + path);
		System.out.println("fileName : " + fileName);
		System.out.println("fileName : " + decleanXSS(fileName));
		System.out.println("======================================================");

		final URL url = new URL(CloudFrontUtil.getCustomSignedUrl(path, 100));
		final Resource resource = new UrlResource(url);
		final HttpHeaders headers = new HttpHeaders();
		final String encodeFileName = URLEncoder.encode(decleanXSS(fileName),"UTF-8").trim().replace("+", "%20");

		//headers.set("Content-Transfer-Encoding", "binary");
		//headers.set("Content-Disposition", "attachment;filename=" + encodeFileName + ";filename*= UTF-8''" + encodeFileName);
		//headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + encodeFileName + ";filename*= UTF-8''" + encodeFileName);

		//headers.add(HttpHeaders.TRANSFER_ENCODING, "binary");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream; charset=utf-8");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodeFileName +"\"");
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

	public static void s3FileDownload2(String path,final String fileName) throws IOException {
		log.info("FileUtil.s3FileDownload");
		System.out.println("======================================================");
		System.out.println("path : " + path);
		System.out.println("fileName : " + fileName);
		System.out.println("fileName : " + decleanXSS(fileName));
		System.out.println("======================================================");

		final URL url = new URL(CloudFrontUtil.getCustomSignedUrl(path, 100));
		final Resource resource = new UrlResource(url);
		final HttpHeaders headers = new HttpHeaders();
		final String encodeFileName = URLEncoder.encode(decleanXSS(fileName),"UTF-8").trim().replace("+", "%20");

		headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream; charset=utf-8");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodeFileName +"\"");
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));

		new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

	public static ResponseEntity<Resource> s3FileDownload_original(String path,final String fileName) throws IOException {
		log.info("FileUtil.s3FileDownload");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		final S3ObjectInputStream s3ObjectInputStream = S3Util.getFile(path);
		final HttpHeaders headers = new HttpHeaders();
		final Resource resource = new ByteArrayResource(IOUtils.toByteArray(s3ObjectInputStream));
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));
		stopWatch.stop();
		stopWatch.prettyPrint();
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

	/**
	 * Exhaust input stream.
	 *
	 * @param is the is
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 27. 오후 4:52:29
	 */
	private static void exhaustInputStream(final InputStream is) {
		log.info("FileUtil.exhaustInputStream");
		// InputStream.read() 에서 블럭상태에 빠지기 때문에 따로 쓰레드를 돌려서 스트림을 소비한다.
		new Thread(() -> {
			try{
				final BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String cmd;
				while((cmd = br.readLine()) != null){ // 읽을 라인이 없을때까지 계속 반복
					log.info("videoResize {}", cmd);
				}
			}catch(IOException e){
				throw (CodeMessageHandleException) new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
			}
		}).start();
	}

	/**
	 * xss 필터 및 path 수정
	 *
	 * @param str  the value
	 * @return the string
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 8. 25. 오후 5:07:38
	 */
	public static String cleanXSS(String str, boolean isFolder) {
		String result = str;
		String [] replaceStr = {"bin","boot","etc","lib","lib64","proc","root","sbin","sys","usr","var"};
		for(String temp : replaceStr){
			result = result.replaceAll(temp, "");
		}

		result = result.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		result = result.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		result = result.replaceAll("'", "&#39;");
		result = result.replaceAll("eval\\((.*)\\)", "");
		result = result.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		result = result.replaceAll("script", "");
		result = result.replaceAll("&", "");

		if(!isFolder){
			result = result.replaceAll("\\\\", "");
			result = result.replaceAll("/", " ");
		}

		result = result.replaceAll("\\.\\.", "");
		result = result.replaceAll("\\.\\./", "");
		result = result.replaceAll("\\./", "");
		result = result.replaceAll("\\.\\\\", "");
		result = result.replaceAll("\\.\\.\\\\", "");
		return result;
	}

	public static String decleanXSS(String str) {
		String result = str;
		result = result.replaceAll("&lt;","<")
						.replaceAll("&gt;",">");
		result = result.replaceAll("#40;", "\\(")
						.replaceAll("#41;", "\\)");
		result = result.replaceAll("&#39;", "'");
		return result;
	}

	/**
	 * 화이트 문자열 체트 [이미지용]
	 *
	 * @param paramStr the param str
	 * @param folder   the folder
	 * @return the string
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 8. 31. 오후 12:25:53
	 */
	private static String whiteListing(String paramStr, String folder) {
		String folderParam = whiteFolderSelect(folder);
		// [공백 폴더명] 권한 없음 처리
		if (ObjectUtils.isEmpty(folderParam)) {
			throw new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_FILE.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
			);
		} else {
			folderParam = cleanXSS(folderParam,false);
		}

		// [허용 가능 목록에 없는 폴더명 / 공백 폴더명] 권한 없음 처리
		if (!whiteFolderList(folderParam) ) {
			throw new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_FILE.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
			);
		}

		//File files = new File(cleanXSS(root,false) + File.separator + folderParam);
		File files = Paths.get(root, folderParam).toFile();
		boolean checkfile = false;
		for(File file : files.listFiles()){
			if(paramStr.contains(file.getName())){
				checkfile= true;
				break;
			}
		}
		if(checkfile){
			final String[] checkStrArray = {imageMagick + File.separator + imageMagickCommand, ffmpeg + File.separator + ffmpegCommand};
			boolean check = false;
			for(String checkStr : checkStrArray){
				if(paramStr.indexOf(checkStr) == 0){
					check = true;
				}
			}
			if(check){
				return paramStr;
			}
		}
		return "";
	}

	/**
	 * 화이트 문자열 체트 [동영상용]
	 *
	 * @param folder        the folder
	 * @param paramStrArray the param str array
	 * @return the boolean
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 9. 7. 오후 3:47:30
	 */
	private static List<String> whiteListing(String folder, String... paramStrArray){
		String folderParam = whiteFolderSelect(folder);
		// [공백 폴더명] 권한 없음 처리
		if (ObjectUtils.isEmpty(folderParam)) {
			throw new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_FILE.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
			);
		} else {
			folderParam = cleanXSS(folderParam,false);
		}

		// [허용 가능 목록에 없는 폴더명 / 공백 폴더명] 권한 없음 처리
		if (!whiteFolderList(folderParam) ) {
			throw new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_FILE.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name())
			);
		}

		//File files = new File(root + File.separator + cleanXSS(folderParam, false));
		File files = Paths.get(root, folderParam).toFile();

		final List<String> command;
		command = new ArrayList<>(paramStrArray.length);
		for(String arg : paramStrArray)
			command.add(arg);
		boolean check = false;
		for(File file : files.listFiles()){
			log.debug("file.getName() > " + file.getName());
			for(String paramStr : command){
				if(paramStr.contains(file.getName())){
					check = true;
					break;
				}
			}
		}
		if(check){
			return command;
		}
		return null;
	}

	private static String whiteFolderSelect(final String folder) {
		for (ServiceCode.FileFolderEnumCode code : ServiceCode.FileFolderEnumCode.values()) {
			if (code.getFolder().equals(folder)) {
				return code.getFolder();
			}
		}
		return "";
	}

	private static boolean whiteFolderList(final String folder) {
		for (ServiceCode.FileFolderEnumCode code : ServiceCode.FileFolderEnumCode.values()) {
			if (code.getFolder().equals(folder)) {
				return true;
			}
		}
		return false;
	}

	private static boolean whiteExtensionList(final String extension) {
		for (ServiceCode.FileExtensionEnumCode code : ServiceCode.FileExtensionEnumCode.values()) {
			if (code.name().equals(extension.toUpperCase(Locale.getDefault()))) {
				return true;
			}
		}
		return false;
	}

}
