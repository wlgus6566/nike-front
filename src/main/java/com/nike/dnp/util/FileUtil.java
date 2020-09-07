package com.nike.dnp.util;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.common.variable.ServiceCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.exception.CodeMessageHandleException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
		final String newFilepath = root + File.separator + cleanXSS(folder, false);
		final File result = new File(newFilepath+File.separator + cleanXSS(makeFileName(), false) + "." + extension);
		new File(newFilepath).mkdirs();
		return result;
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
		final StopWatch stopWatch = new StopWatch();
		stopWatch.start("fileUpload_" + uploadFile.getOriginalFilename());

		final String extension = StringUtils.getFilenameExtension(uploadFile.getOriginalFilename());

		final File toFile = makeNewFile(cleanXSS(folder, false), cleanXSS(extension, false));
		uploadFile.transferTo(toFile);
		stopWatch.getTotalTimeSeconds();
		stopWatch.stop();
		log.info("stopWatch.getLastTaskTimeMillis() {} :  {} ms",stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());
		final FileResultDTO fileResultDTO = new FileResultDTO();
		fileResultDTO.setFileName(cleanXSS(uploadFile.getOriginalFilename(), false));
		fileResultDTO.setFilePhysicalName(toFile.getPath().replace(root, ""));
		fileResultDTO.setFileSize(toFile.length());
		fileResultDTO.setFileContentType(uploadFile.getContentType());
		fileResultDTO.setFileExtension(extension);
		if(resize && (uploadFile.getContentType().toUpperCase(Locale.getDefault()).contains("IMAGE") || extension.toUpperCase(Locale.getDefault()).contains("PSD") || extension.toUpperCase(
				Locale.getDefault()).contains("AI"))){
			String resizeExtension;
			if(StringUtils.isEmpty(resizeExt)){
				resizeExtension = "jpg";
			}else{
				resizeExtension = resizeExt;
			}
			stopWatch.start("700resize_"+uploadFile.getOriginalFilename());

			List<String> allowedCommands = new ArrayList<>();
			allowedCommands.add("notepad");
			allowedCommands.add("calc");


			// 이미지 사이즈 700x700 으로 변환
			final String detailPath = StringUtils.stripFilenameExtension(toFile.getPath()) + "_detail." + cleanXSS(resizeExtension,false);
			//final String detailPath = root+File.separator+cleanXSS(folder)+File.separator +cleanXSS(StringUtils.stripFilenameExtension(toFile.getName())) + "_detail." + resizeExtension;
			final StringBuilder detailCommand = new StringBuilder(imageMagick);
			detailCommand.append(File.separator).append(imageMagickCommand + " ").append(toFile.getPath());
			if(extension.toUpperCase(Locale.getDefault()).contains("PSD") || extension.toUpperCase(Locale.getDefault()).contains("AI") || extension.toUpperCase(Locale.getDefault()).contains("TIF")
					|| extension.toUpperCase(Locale.getDefault()).contains("GIF")){
				detailCommand.append("[0]");
			}
			detailCommand.append(" -resize 700x700 -background white -gravity center -extent 700x700 ").append(detailPath);
			try{
				final String cmd = whiteListing(detailCommand.toString(), folder);
				if (cmd.isEmpty() || "".equals(cmd)) {
					throw new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
				}
				final Process procDetail = Runtime.getRuntime().exec(cmd);
				procDetail.waitFor();
			}catch(InterruptedException exception){
				log.error("exception", exception);
				// 리사이즈 문제
				//throw (CodeMessageHandleException) new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
			}
			stopWatch.stop();
			log.info("stopWatch.getLastTaskTimeMillis() {} :  {} ms", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());
			final File detailFile = new File(cleanXSS(detailPath,true));
			if(detailFile.isFile()){
				String detailThumbnail = uploadFile.getOriginalFilename();
				detailThumbnail = detailThumbnail.replace("." + StringUtils.getFilenameExtension(detailThumbnail), "") + "_detail." + resizeExtension;
				fileResultDTO.setDetailThumbnailFileName(detailThumbnail);
				fileResultDTO.setDetailThumbnailFilePhysicalName(detailFile.getPath().replace(root, ""));
				fileResultDTO.setDetailThumbnailFileSize(detailFile.length());
			}

			stopWatch.start("100resize_" + uploadFile.getOriginalFilename());
			// 이미지 사이즈 100x100으로 변환
			final String thumbnailPath = StringUtils.stripFilenameExtension(toFile.getPath()) + "_thumbnail." + cleanXSS(resizeExtension,false);
			final StringBuilder command = new StringBuilder(imageMagick);
			command.append(File.separator).append(imageMagickCommand+" ").append(detailFile.getPath());
			if(extension.toUpperCase(Locale.getDefault()).contains("PSD") || extension.toUpperCase(Locale.getDefault()).contains("AI")){
				command.append("[0]");
			}
			command.append(" -resize 100x100 -background white -gravity center -extent 100x100 ").append(thumbnailPath);

			try{
				/*final Runtime runtime = Runtime.getRuntime();
				final Process proc = runtime.exec(whiteListing(command.toString()));*/
				final String cmd = whiteListing(command.toString(), folder);
				if (cmd.isEmpty() || "".equals(cmd)) {
					throw new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
				}
				final Process proc = Runtime.getRuntime().exec(cmd);
				proc.waitFor();
			}catch(InterruptedException exception){
				log.error("exception", exception);
				// 리사이즈 문제
				// throw (CodeMessageHandleException) new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
			}
			stopWatch.stop();
			log.info("stopWatch.getLastTaskTimeMillis() {} :  {} ms", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());

			final File thumbnailFile = new File(cleanXSS(thumbnailPath, true));
			if(thumbnailFile.isFile()){
				String thumbnail = uploadFile.getOriginalFilename();
				thumbnail = thumbnail.replace("." + StringUtils.getFilenameExtension(thumbnail), "") + "_thumbnail." + resizeExtension;
				fileResultDTO.setThumbnailFileName(thumbnail);
				fileResultDTO.setThumbnailFilePhysicalName(thumbnailFile.getPath().replace(root, ""));
				fileResultDTO.setThumbnailFileSize(thumbnailFile.length());
			}

		}else if(resize && (uploadFile.getContentType().toUpperCase(Locale.getDefault()).contains("VIDEO"))){

			// 사이즈 변환시 700:394 를 변경 하면 됨
			final String thumbnailPath = StringUtils.stripFilenameExtension(toFile.getPath()) + "_thumbnail.mp4";

			final String[] command = {ffmpeg + File.separator + ffmpegCommand,"-y","-i",toFile.getPath(),"-vf"
					,"scale=700:394:force_original_aspect_ratio=decrease,pad=700:394:(ow-iw/2):(oh-ih)/2:white"
					,thumbnailPath};
			boolean check = whiteListing(folder, command);
			if (!check) {
				throw new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
			}
			stopWatch.start("videoResize_"+uploadFile.getOriginalFilename());

			final ProcessBuilder processBuilder = new ProcessBuilder(command);
			processBuilder.redirectErrorStream(true);
			Process process = null;
			try{
				process = processBuilder.start();
			}catch(Exception exception){
				process.destroy();
				log.error("exception", exception);
				//throw (CodeMessageHandleException) new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
			}
			exhaustInputStream(process.getInputStream());
			try{
				process.waitFor();
			}catch(InterruptedException exception){
				process.destroy();
				//throw (CodeMessageHandleException) new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
			}

			// 정상 종료가 되지 않았을 경우
			if(process.exitValue() != 0){
				throw new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(), MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
			}
			stopWatch.stop();
			log.info("stopWatch.getLastTaskTimeMillis() {} :  {} ms", stopWatch.getLastTaskName(), stopWatch.getLastTaskTimeMillis());
			final File detailFile = new File(cleanXSS(thumbnailPath, false));
			if(detailFile.isFile()){
				String detailThumbnail = uploadFile.getOriginalFilename();
				detailThumbnail = detailThumbnail.replace("." + StringUtils.getFilenameExtension(detailThumbnail), "") + "_detail.mp4";
				fileResultDTO.setDetailThumbnailFileName(detailThumbnail);
				fileResultDTO.setDetailThumbnailFilePhysicalName(detailFile.getPath().replace(root, ""));
				fileResultDTO.setDetailThumbnailFileSize(detailFile.length());
			}
		}
		log.info("stopWatch.getTotalTimeSeconds() {} :  {} s", uploadFile.getOriginalFilename(),stopWatch.getTotalTimeSeconds());
		log.info("stopWatch.prettyPrint() {}", stopWatch.prettyPrint());
		log.info("FileUtil.fileSave  end");
		return fileResultDTO;
	}

	/**
	 * 파일을 temp에 저장 및 이미지 리사이즈
	 *
	 * @param uploadFile the upload file
	 * @return the file result dto
	 * @throws IOException the io exception
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
		return fileSave(uploadFile, folder, false, null);
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
	public static void deleteTemp() {
		log.info("FileUtil.deleteTemp");
		final File tempFile = new File(root+File.separator+ ServiceCode.FileFolderEnumCode.TEMP.getFolder());
		final File[] files = tempFile.listFiles();

		for(final File file : files){
			LocalDateTime updateDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), TimeZone.getDefault().toZoneId());
			updateDate = updateDate.plusHours(24);
			final LocalDateTime today = LocalDateTime.now();
			if(updateDate.isBefore(today) && file.isFile()){
				final String awsDeleteFile = file.getPath().replace(root, "");
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

//		String contentType = Files.probeContentType(path);
//		headers.add(HttpHeaders.CONTENT_TYPE,contentType); //이미지일경우 바로 뷰

		headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + path.getFileName().toString());
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(path.toFile().length()));

//		final Resource resource = new InputStreamResource(Files.newInputStream(path));
		final Resource resource = new FileSystemResource(new File(path.toUri()));
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

		final S3ObjectInputStream s3ObjectInputStream = S3Util.getFile(path);
		final HttpHeaders headers = new HttpHeaders();
		final Resource resource = new ByteArrayResource(IOUtils.toByteArray(s3ObjectInputStream));
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));
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
	 * @param value  the value
	 * @param folder 폴더 구분
	 * @return the string
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 8. 25. 오후 5:07:38
	 */
	public static String cleanXSS(String value, boolean folder) {
		String [] replaceStr = {"bin","boot","etc","home","lib","lib64","proc","root","sbin","sys","usr","var"};
		for(String str : replaceStr){
			value = value.replaceAll(str, "");
		}


		value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		value = value.replaceAll("&", "");
		if(!folder){
			value = value.replaceAll("\\\\", "");
			value = value.replaceAll("/", " ");
		}

		value = value.replaceAll("\\.\\.", "");
		value = value.replaceAll("\\.\\./", "");
		value = value.replaceAll("\\./", "");
		value = value.replaceAll("\\.\\\\", "");
		value = value.replaceAll("\\.\\.\\\\", "");
		return value;
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
		File files = new File(root + File.separator + cleanXSS(folder, false));
		boolean checkfile = false;
		for(File file : files.listFiles()){
			log.debug("file.getName() > " + file.getName());
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
	private static boolean whiteListing(String folder, String... paramStrArray){

		File files = new File(root + File.separator + cleanXSS(folder, false));

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
		return check;
	}
}
