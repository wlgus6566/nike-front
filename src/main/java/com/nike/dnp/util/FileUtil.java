package com.nike.dnp.util;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
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
public class FileUtil {

	/**
	 * The constant root
	 *
	 * @author [윤태호]
	 */
	private static String root;

	/**
	 * Set root.
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
	 * 새로운 파일 생서
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

}
