package com.nike.dnp.util;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.exception.CodeMessageHandleException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * ImageUtil
 *
 * @author [윤태호]
 * @since 2020. 7. 10. 오후 2:41:58
 * @implNote
 */
@Component
@Slf4j
@NoArgsConstructor
public class ImageUtil {

	/**
	 * The constant root
	 *
	 * @author [윤태호]
	 */
	private static String root;

	/**
	 * Sets root.
	 *
	 * @param root the root
	 * @author [윤태호]
	 * @since 2020. 7. 10. 오후 2:41:58
	 * @implNote
	 */
	@Value("${nike.file.root:}")
	public void setRoot(final String root) {
		this.root = root;
	}


	/**
	 * 이미지 base64 를 이미지로 변환
	 * S3 업로드
	 *
	 * @param folder    폴더
	 * @param base64Str base64 스트링
	 * @return FileUploadDTO
	 * @throws IOException                the io exception
	 * @throws CodeMessageHandleException the code message handle exception
	 * @author [윤태호]
	 * @since 2020. 7. 10. 오후 2:41:58
	 * @implNote
	 */
	public static FileResultDTO fileSaveForBase64(final String folder, final String base64Str) {
		log.info("ImageUtil.fileSaveForBase64");
		final String info = base64Str.split(",")[0];
		if(!info.contains("image")){
			throw new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_FILE.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
		}

		final String base64 = base64Str.split(",")[1];
		final byte[] imageByte = DatatypeConverter.parseBase64Binary(base64);
		final ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		BufferedImage image;
		try{
			image = ImageIO.read(bis);
			bis.close();
		}catch(IOException e){
			throw (CodeMessageHandleException)new CodeMessageHandleException(FailCode.ConfigureError.INVALID_FILE.name(),
																			 MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
		}
		String extension = "";
		final String type = info.substring(info.indexOf('/') + 1, info.indexOf(';'));
		switch(type){
			case "gif":
				extension = "gif";break;
			case "x-png":
			case "png":
				extension = "png";
				break;
			case "jpeg":
			case "pjpeg":
			case "jpg" :
			default: extension= "jpg";break;
		}
		final File newFile = FileUtil.makeNewFile(folder,extension);
		try{
			ImageIO.write(image, type, newFile);
		}catch(IOException e){
			throw (CodeMessageHandleException) new CodeMessageHandleException(
					FailCode.ConfigureError.INVALID_FILE.name()
					, MessageUtil.getMessage(FailCode.ConfigureError.INVALID_FILE.name()));
		}

		final FileResultDTO fileResultDTO = new FileResultDTO();
		fileResultDTO.setFileName(newFile.getName());
		fileResultDTO.setFilePhysicalName(StringUtils.remove(newFile.getPath(), root));
		fileResultDTO.setFileSize(newFile.length());
		// S3 업로드
		S3Util.upload(fileResultDTO, "Y", "Y");

		return fileResultDTO;

	}

}
