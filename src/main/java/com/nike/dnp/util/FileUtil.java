package com.nike.dnp.util;

import com.nike.dnp.common.variable.ServiceEnumCode;
import com.nike.dnp.dto.file.FileResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

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
		String newFilepath = root + File.separator + folder;
		String newFileName = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE) + LocalDateTime.now().get(ChronoField.MICRO_OF_SECOND)
				+ RandomStringUtils.random(10, true, true) + "." + extension;
		File result = new File(newFilepath+File.separator+ newFileName);
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
	 * @param resizeWidth the resize width
	 * @return the file result dto
	 * @throws IOException          the io exception
	 * @throws InterruptedException the interrupted exception
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 13. 오후 4:55:25
	 * @Description
	 */
	public static FileResultDTO fileSave(MultipartFile uploadFile, String folder, boolean resize, String resizeExt, int resizeWidth) throws IOException, InterruptedException {

		if(StringUtils.isEmpty(resizeExt)){
			resizeExt = "jpg";
		}
		String extension = StringUtils.getFilenameExtension(uploadFile.getOriginalFilename());
		File toFile = makeNewFile(folder, extension);
		uploadFile.transferTo(toFile);
		FileResultDTO fileResultDTO = new FileResultDTO();
		fileResultDTO.setFileName(uploadFile.getOriginalFilename());
		fileResultDTO.setFilePhysicalName(toFile.getPath().replace(root, ""));
		fileResultDTO.setFileSize(toFile.length());
		fileResultDTO.setFileContentType(uploadFile.getContentType());

		if(resize && (uploadFile.getContentType().toUpperCase().contains("IMAGE") || extension.toUpperCase().contains("PSD") || extension.toUpperCase().contains("AI"))){
			if(resizeWidth == 0){
				resizeWidth = 120;
			}
			String thumbnailPath = StringUtils.stripFilenameExtension(toFile.getPath()) + "_thumbnail." + resizeExt;
			String command = imageMagick + File.separator + "magick ";
			if(extension.toUpperCase().contains("PSD") || extension.toUpperCase().contains("AI")){
				command += toFile.getPath() + "[0]";
			}else{
				command += toFile.getPath();
			}
			command += " -resize " + resizeWidth + " " + thumbnailPath;

			log.debug("command {}", command);
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(command);
			int i = proc.waitFor();
			File thumbnailFile = new File(thumbnailPath);
			if(thumbnailFile.isFile()){
				String thumbnail = uploadFile.getOriginalFilename();
				thumbnail = thumbnail.replace("." + StringUtils.getFilenameExtension(thumbnail), "") + "_thumbnail." + resizeExt;
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
	public static FileResultDTO fileTempSaveAndImageResize(MultipartFile uploadFile, int resize) throws IOException, InterruptedException {
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
	public static FileResultDTO fileTempSaveAndImageResize(MultipartFile uploadFile, String resizeExt, int resize) throws IOException, InterruptedException {
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
	public static FileResultDTO fileTempSaveAndImageResize(MultipartFile uploadFile) throws IOException, InterruptedException {
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
	public static FileResultDTO fileSave(MultipartFile uploadFile,String folder) throws IOException, InterruptedException {
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
	public static FileResultDTO fileTempSave(MultipartFile uploadFile) throws IOException, InterruptedException {
		return fileSave(uploadFile, ServiceEnumCode.FileFolderEnumCode.TEMP.getFolder());
	}


	/**
	 * temp 폴더 파일 삭제 	 *
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 13. 오후 4:55:25
	 * @Description
	 */
	public static void deleteTemp() {
		File tempFile = new File(root+File.separator+ ServiceEnumCode.FileFolderEnumCode.TEMP.getFolder());
		File[] files = tempFile.listFiles();
		for(File file : files){
			if(file.isFile()){
				file.delete();
			}
		}
	}

}
