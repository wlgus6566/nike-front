package com.nike.dnp.util;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.exception.CodeMessageHandleException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.TimeZone;

/**
 * FileUtil
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 10. 오후 2:39:36
 * @Description
 */
@Component
@Slf4j
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
	 * @CreatedOn 2020. 7. 10. 오후 2:39:36
	 * @Description
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
	 * @CreatedOn 2020. 7. 13. 오후 2:30:09
	 * @Description
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
	 * @CreatedOn 2020. 7. 17. 오전 11:55:43
	 * @Description
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
	 * @CreatedOn 2020. 7. 27. 오후 4:58:36
	 * @Description
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
	 * @CreatedOn 2020. 7. 27. 오후 4:58:36
	 * @Description
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
	 * @CreatedOn 2020. 7. 10. 오후 2:39:36
	 * @Description
	 */
	public static File makeNewFile(final String folder,final String extension) {
		final String newFilepath = root + File.separator + folder;
		final String newFileName = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE) + LocalDateTime.now().get(ChronoField.MICRO_OF_SECOND)
				+ RandomStringUtils.random(10, true, true) + "." + extension;
		final File result = new File(newFilepath+File.separator+ newFileName);
		if(result.isFile()){
			return makeNewFile(folder,extension);
		}else{
			new File(newFilepath).mkdirs();
			return result;
		}
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
	 * @CreatedOn 2020. 7. 13. 오후 4:55:25
	 * @Description
	 */
	public static FileResultDTO fileSave(final MultipartFile uploadFile,
										 final String folder,
										 final boolean resize,
										 final String resizeExt) throws IOException {


		final String extension = StringUtils.getFilenameExtension(uploadFile.getOriginalFilename());

		final File toFile = makeNewFile(folder, extension);
		uploadFile.transferTo(toFile);
		final FileResultDTO fileResultDTO = new FileResultDTO();
		fileResultDTO.setFileName(uploadFile.getOriginalFilename());
		fileResultDTO.setFilePhysicalName(toFile.getPath().replace(root, ""));
		fileResultDTO.setFileSize(toFile.length());
		fileResultDTO.setFileContentType(uploadFile.getContentType());
		fileResultDTO.setFileExtension(extension);
		if(resize && (uploadFile.getContentType().toUpperCase(Locale.getDefault()).contains("IMAGE") || extension.toUpperCase(Locale.getDefault()).contains("PSD") || extension.toUpperCase(
				Locale.getDefault()).contains("AI"))){
			String resizeExtension = "";
			if(StringUtils.isEmpty(resizeExt)){
				resizeExtension = "jpg";
			}else{
				resizeExtension = resizeExt;
			}

			// 이미지 사이즈 100x100으로 변환
			final String thumbnailPath = StringUtils.stripFilenameExtension(toFile.getPath()) + "_thumbnail." + resizeExtension;
			final StringBuilder command = new StringBuilder(imageMagick);
			command.append(File.separator).append(imageMagickCommand+" ").append(toFile.getPath());
			if(extension.toUpperCase(Locale.getDefault()).contains("PSD") || extension.toUpperCase(Locale.getDefault()).contains("AI")){
				command.append("[0]");
			}
			command.append(" -resize 100x100 -background white -gravity center -extent 100x100 ").append(thumbnailPath);

			try{
				final Runtime runtime = Runtime.getRuntime();
				final Process proc = runtime.exec(command.toString());
				proc.waitFor();
			}catch(InterruptedException e){
				// 리사이즈 문제
				throw (CodeMessageHandleException) new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
			}
			final File thumbnailFile = new File(thumbnailPath);
			if(thumbnailFile.isFile()){
				String thumbnail = uploadFile.getOriginalFilename();
				thumbnail = thumbnail.replace("." + StringUtils.getFilenameExtension(thumbnail), "") + "_thumbnail." + resizeExtension;
				fileResultDTO.setThumbnailFileName(thumbnail);
				fileResultDTO.setThumbnailPhysicalName(thumbnailFile.getPath().replace(root, ""));
				fileResultDTO.setThumbnailSize(thumbnailFile.length());
			}

			// 이미지 사이즈 700x700 으로 변환
			final String detailPath = StringUtils.stripFilenameExtension(toFile.getPath()) + "_detail." + resizeExtension;
			final StringBuilder detailCommand = new StringBuilder(imageMagick);
			detailCommand.append(File.separator).append(imageMagickCommand + " ").append(toFile.getPath());
			if(extension.toUpperCase(Locale.getDefault()).contains("PSD") || extension.toUpperCase(Locale.getDefault()).contains("AI")){
				detailCommand.append("[0]");
			}
			detailCommand.append(" -resize 700x700 -background white -gravity center -extent 700x700 ").append(detailPath);

			try{
			final Runtime runtimeDetail = Runtime.getRuntime();
				final Process procDetail = runtimeDetail.exec(detailCommand.toString());
				procDetail.waitFor();
			}catch(InterruptedException e){
				// 리사이즈 문제
				throw (CodeMessageHandleException) new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
			}
			final File detailFile = new File(detailPath);
			if(detailFile.isFile()){
				String detailThumbnail = uploadFile.getOriginalFilename();
				detailThumbnail = detailThumbnail.replace("." + StringUtils.getFilenameExtension(detailThumbnail), "") + "_detail." + resizeExtension;
				fileResultDTO.setDetailThumbnailFileName(detailThumbnail);
				fileResultDTO.setDetailThumbnailPhysicalName(detailFile.getPath().replace(root, ""));
				fileResultDTO.setDetailThumbnailSize(detailFile.length());
			}

		}else if(resize && (uploadFile.getContentType().toUpperCase(Locale.getDefault()).contains("VIDEO"))){
			// 사이즈 변환시 700:394 를 변경 하면 됨
			final String thumbnailPath = StringUtils.stripFilenameExtension(toFile.getPath()) + "_thumbnail.mp4";
			String[] command = new String[]{ffmpeg+File.separator+ffmpegCommand,"-y","-i",toFile.getPath(),"-vf"
					,"scale=700:394:force_original_aspect_ratio=decrease,pad=700:394:(ow-iw/2):(oh-ih)/2:white"
					,thumbnailPath};
			log.debug("command.toString() {}", command.toString());
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			processBuilder.redirectErrorStream(true);
			Process process = null;

			try{
				process = processBuilder.start();
			}catch(Exception e){
				process.destroy();
				throw (CodeMessageHandleException) new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
			}

			exhaustInputStream(process.getInputStream());

			try{
				process.waitFor();
			}catch(InterruptedException e){
				process.destroy();
				throw (CodeMessageHandleException) new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
			}

			// 정상 종료가 되지 않았을 경우
			if(process.exitValue() != 0){
				System.out.println("변환 중 에러 발생");
			}


			final File detailFile = new File(thumbnailPath);
			if(detailFile.isFile()){
				String detailThumbnail = uploadFile.getOriginalFilename();
				detailThumbnail = detailThumbnail.replace("." + StringUtils.getFilenameExtension(detailThumbnail), "") + "_detail.mp4";
				fileResultDTO.setDetailThumbnailFileName(detailThumbnail);
				fileResultDTO.setDetailThumbnailPhysicalName(detailFile.getPath().replace(root, ""));
				fileResultDTO.setDetailThumbnailSize(detailFile.length());
			}
		}
		return fileResultDTO;
	}

	/**
	 * 파일을 temp에 저장 및 이미지 리사이즈
	 *
	 * @param uploadFile the upload file
	 * @return the file result dto
	 * @throws IOException          the io exception
	 * @throws InterruptedException the interrupted exception
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 13. 오후 4:55:25
	 * @Description
	 */
	public static FileResultDTO fileTempSaveAndImageResize(final MultipartFile uploadFile) throws IOException, InterruptedException {
		return fileSave(uploadFile, ServiceCode.FileFolderEnumCode.TEMP.getFolder(), true, null);
	}

	/**
	 * 파일을 temp에 저장 및 이미지 리사이즈
	 *
	 * @param uploadFile the upload file
	 * @param resizeExt  리사이즈 확장 명
	 * @return the file result dto
	 * @throws IOException          the io exception
	 * @throws InterruptedException the interrupted exception
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 13. 오후 4:55:25
	 * @Description
	 */
	public static FileResultDTO fileTempSaveAndImageResize(final MultipartFile uploadFile,
														   final String resizeExt) throws IOException, InterruptedException {
		return fileSave(uploadFile, ServiceCode.FileFolderEnumCode.TEMP.getFolder(), true, resizeExt);
	}

	/**
	 * 파일 저장
	 *
	 * @param uploadFile the upload file
	 * @param folder     the folder
	 * @return the file result dto
	 * @throws IOException          the io exception
	 * @throws InterruptedException the interrupted exception
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 13. 오후 4:55:25
	 * @Description
	 */
	public static FileResultDTO fileSave(final MultipartFile uploadFile,
										 final String folder) throws IOException, InterruptedException {
		return fileSave(uploadFile, folder, false, null);
	}

	/**
	 * 파일 temp 폴더에 저장
	 *
	 * @param uploadFile the upload file
	 * @return the file result dto
	 * @throws IOException          the io exception
	 * @throws InterruptedException the interrupted exception
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 13. 오후 4:55:25
	 * @Description
	 */
	public static FileResultDTO fileTempSave(final MultipartFile uploadFile) throws IOException, InterruptedException {
		return fileSave(uploadFile, ServiceCode.FileFolderEnumCode.TEMP.getFolder());
	}


	/**
	 * temp 폴더 파일 삭제 (등록후 24시간 지난 파일들만 삭제 처리)
	 *
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 13. 오후 4:55:25
	 * @Description
	 */
	public static void deleteTemp() {
		final File tempFile = new File(root+File.separator+ ServiceCode.FileFolderEnumCode.TEMP.getFolder());
		final File[] files = tempFile.listFiles();

		for(final File file : files){
			LocalDateTime updateDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), TimeZone.getDefault().toZoneId());
			updateDate = updateDate.plusHours(24);
			LocalDateTime today = LocalDateTime.now();
			if(updateDate.isBefore(today) && file.isFile()){
				String awsDeleteFile = file.getPath().replace(root, "");
				file.delete();
				try{
					S3Util.fileDelete(awsDeleteFile);
				}catch(Exception e){
					// TODO [YTH] 아마존 파일 없음
				}
			}
		}
	}

	/**
	 * File download single result.
	 *
	 * @param filePath the file path
	 * @return the single result
	 * @author [이소정]
	 * @CreatedOn 2020. 7. 16. 오후 6:15:26
	 * @Description
	 */
	public static ResponseEntity<Resource> fileDownload(final String filePath) {
		final Path path = Paths.get(filePath);

		final HttpHeaders headers = new HttpHeaders();

//		String contentType = Files.probeContentType(path);
//		headers.add(HttpHeaders.CONTENT_TYPE,contentType); //이미지일경우 바로 뷰

		headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + path.getFileName().toString());
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(path.toFile().length()));

//		final Resource resource = new InputStreamResource(Files.newInputStream(path));
		final Resource resource = new FileSystemResource(new File(path.toUri()));
		return new ResponseEntity<>(resource,headers, HttpStatus.OK);
	}


	/**
	 * Exhaust input stream.
	 *
	 * @param is the is
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 27. 오후 4:52:29
	 * @Description
	 */
	private static void exhaustInputStream(final InputStream is) {

		// InputStream.read() 에서 블럭상태에 빠지기 때문에 따로 쓰레드를 돌려서 스트림을 소비한다.
		new Thread(() -> {
			try{
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String cmd;
				while((cmd = br.readLine()) != null){ // 읽을 라인이 없을때까지 계속 반복
					log.debug("cmd {}", cmd);
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}).start();

	}
}
