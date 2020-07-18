package com.nike.dnp.util;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.file.FileResultDTO;
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

import java.io.File;
import java.io.IOException;
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
	 * @param uploadFile  the upload file
	 * @param folder      the folder
	 * @param resize      the resize
	 * @param resizeExt   the resize ext
	 * @param width the resize width
	 * @return the file result dto
	 * @throws IOException          the io exception
	 * @throws InterruptedException the interrupted exception
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 13. 오후 4:55:25
	 * @Description
	 */
	public static FileResultDTO fileSave(final MultipartFile uploadFile,
										 final String folder,
										 final boolean resize,
										 final String resizeExt,
										 final int width) throws IOException, InterruptedException {


		final String extension = StringUtils.getFilenameExtension(uploadFile.getOriginalFilename());

		final File toFile = makeNewFile(folder, extension);
		uploadFile.transferTo(toFile);
		final FileResultDTO fileResultDTO = new FileResultDTO();
		fileResultDTO.setFileName(uploadFile.getOriginalFilename());
		fileResultDTO.setFilePhysicalName(toFile.getPath().replace(root, ""));
		fileResultDTO.setFileSize(toFile.length());
		fileResultDTO.setFileContentType(uploadFile.getContentType());

		if(resize && (uploadFile.getContentType().toUpperCase(Locale.getDefault()).contains("IMAGE") || extension.toUpperCase(Locale.getDefault()).contains("PSD") || extension.toUpperCase(
				Locale.getDefault()).contains("AI"))){
			String resizeExtension = "";
			int resizeWidth = 120;
			if(StringUtils.isEmpty(resizeExt)){
				resizeExtension = "jpg";
			}else{
				resizeExtension = resizeExt;
			}
			if(width > 0){
				resizeWidth = width;
			}
			final String thumbnailPath = StringUtils.stripFilenameExtension(toFile.getPath()) + "_thumbnail." + resizeExtension;
			final StringBuilder command = new StringBuilder(imageMagick);
			command.append(File.separator).append(imageMagickCommand+" ").append(toFile.getPath());
			if(extension.toUpperCase(Locale.getDefault()).contains("PSD") || extension.toUpperCase(Locale.getDefault()).contains("AI")){
				command.append("[0]");
			}
			command.append(" -resize ").append(resizeWidth).append(' ').append(thumbnailPath);

			final Runtime runtime = Runtime.getRuntime();
			final Process proc = runtime.exec(command.toString());
			proc.waitFor();
			final File thumbnailFile = new File(thumbnailPath);
			if(thumbnailFile.isFile()){
				String thumbnail = uploadFile.getOriginalFilename();
				thumbnail = thumbnail.replace("." + StringUtils.getFilenameExtension(thumbnail), "") + "_thumbnail." + resizeExtension;
				fileResultDTO.setThumbnailFileName(thumbnail);
				fileResultDTO.setThumbnailPhysicalName(thumbnailFile.getPath().replace(root, ""));
				fileResultDTO.setThumbnailSize(thumbnailFile.length());
			}
		}
		return fileResultDTO;
	}

	/**
	 * 파일을 temp에 저장 및 이미지 리사이즈
	 *
	 * @param uploadFile the upload file
	 * @param resize     리사이즈 가로 사이즈
	 * @return the file result dto
	 * @throws IOException          the io exception
	 * @throws InterruptedException the interrupted exception
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 13. 오후 4:55:25
	 * @Description
	 */
	public static FileResultDTO fileTempSaveAndImageResize(final MultipartFile uploadFile,
														   final int resize) throws IOException, InterruptedException {
		return fileSave(uploadFile, ServiceEnumCode.FileFolderEnumCode.TEMP.getFolder(), true, null, resize);
	}

	/**
	 * 파일을 temp에 저장 및 이미지 리사이즈
	 *
	 * @param uploadFile the upload file
	 * @param resizeExt  리사이즈 확장 명
	 * @param resize     리사이즈 가로 사이즈
	 * @return the file result dto
	 * @throws IOException          the io exception
	 * @throws InterruptedException the interrupted exception
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 13. 오후 4:55:25
	 * @Description
	 */
	public static FileResultDTO fileTempSaveAndImageResize(final MultipartFile uploadFile,
														   final String resizeExt,
														   final int resize) throws IOException, InterruptedException {
		return fileSave(uploadFile, ServiceEnumCode.FileFolderEnumCode.TEMP.getFolder(), true, resizeExt, resize);
	}

	/**
	 * 파일을 temp 에 저장 및 이미지 리사이즈<br />(가로사이즈 120)
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
		return fileSave(uploadFile, ServiceEnumCode.FileFolderEnumCode.TEMP.getFolder(), true, null, 0);
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
		return fileSave(uploadFile, folder, false, null, 0);
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
		return fileSave(uploadFile, ServiceEnumCode.FileFolderEnumCode.TEMP.getFolder());
	}


	/**
	 * temp 폴더 파일 삭제 (등록후 24시간 지난 파일들만 삭제 처리)
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 13. 오후 4:55:25
	 * @Description
	 */
	public static void deleteTemp() {
		final File tempFile = new File(root+File.separator+ ServiceEnumCode.FileFolderEnumCode.TEMP.getFolder());
		final File[] files = tempFile.listFiles();

		for(final File file : files){
			LocalDateTime updateDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), TimeZone.getDefault().toZoneId());
			updateDate = updateDate.plusHours(24);
			LocalDateTime today = LocalDateTime.now();
			if(updateDate.isBefore(today) && file.isFile()){
				file.delete();
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

}
