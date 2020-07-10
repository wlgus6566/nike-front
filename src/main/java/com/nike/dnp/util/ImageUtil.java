package com.nike.dnp.util;

import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.dto.file.FileResultDTO;
import com.nike.dnp.exception.CodeMessageHandleException;
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
 * @CreatedOn 2020. 7. 10. 오후 2:41:58
 * @Description
 */
@Component
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
	 * @CreatedOn 2020. 7. 10. 오후 2:41:58
	 * @Description
	 */
	@Value("${nike.file.root:}")
	public void setRoot(String root) {
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
	 * @CreatedOn 2020. 7. 10. 오후 2:41:58
	 * @Description
	 */
	public static FileResultDTO fileSaveForBase64(final String folder, final String base64Str) {

		BufferedImage image = null;
		String base64 = base64Str.split(",")[1];
		String info = base64Str.split(",")[0];

		if(!info.contains("image")){
			throw new CodeMessageHandleException(ErrorEnumCode.FileError.NOT_IMAGE_FILE.name(),ErrorEnumCode.FileError.NOT_IMAGE_FILE.getMessage());
		}
		String type = info.substring(info.indexOf("/") + 1, info.indexOf(";"));
		byte[] imageByte;
		imageByte = DatatypeConverter.parseBase64Binary(base64);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		try{
			image = ImageIO.read(bis);
			bis.close();
		}catch(IOException e){
			throw new CodeMessageHandleException(ErrorEnumCode.FileError.FILE_WRITE_ERROR.name(), ErrorEnumCode.FileError.FILE_WRITE_ERROR.getMessage());
		}

		String extension = "";
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
		File newFile = FileUtil.makeNewFile(folder,extension);
		try{
			ImageIO.write(image, type, newFile);
		}catch(IOException e){
			throw new CodeMessageHandleException(ErrorEnumCode.FileError.FILE_READ_ERROR.name(), ErrorEnumCode.FileError.FILE_READ_ERROR.getMessage());
		}

		FileResultDTO fileResultDTO = new FileResultDTO();
		fileResultDTO.setFileName(newFile.getName());
		fileResultDTO.setFilePhysicalName(StringUtils.remove(newFile.getPath(), root));
		fileResultDTO.setFileSize(newFile.length());

		// TODO [YTH] 이미지 파일 s3 로 업로드 필요함

		return fileResultDTO;

	}

}
