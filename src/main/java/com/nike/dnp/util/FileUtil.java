package com.nike.dnp.util;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * File Util
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description FileUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

public class FileUtil {

	private static String ATTACH_PATH = "/upload";
	private static String DEFAULT_MIMETYPE = "image/png,image/pjpeg,image/gif,image/jpeg,image/jpg,image/bmp,application/msword,application/x-zip-compressed,application/zip,application/vnd.ms-powerpoint,audio/mpeg,application/vnd.ms-excel,text/plain,application/octet-stream,video/x-ms-wmv,application/x-shockwave-flash,application/x-alz,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.openxmlformats-officedocument.presentationml.presentation,application/x-pdf,application/pdf";

	/**
	 * 업로드 경로 가져오기
	 * 
	 * @param request
	 * @param type
	 * @return
	 */
	public static HashMap<String, String> getUploadPath(HttpServletRequest request, String type, String dir) {
		HashMap<String, String> returnMap = new HashMap<String, String>();

		String rootPath = "";
		rootPath = dir;
		String folder = DateUtil.getToday("yyyyMMdd") + "/";
		String savePath = rootPath + "/" + folder;
		String realPath = request.getSession().getServletContext().getRealPath("/") + ATTACH_PATH + "/" + savePath;

		FileUtil.createDirectory(realPath);

		returnMap.put("folder", folder);
		returnMap.put("savePath", savePath);
		returnMap.put("realPath", realPath);

		return returnMap;
	}

	/**
	 * 파일 존재 여부 확인
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isFile(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 물리적 파일 삭제
	 * 
	 * @param realPath
	 * @param fileName
	 */
	public static void deleteFile(String realPath, String fileName) {
		File file = new File(realPath + "/" + fileName);
		if (file.exists())
			file.delete();
	}

	/**
	 * 물리적 파일 복사 후 삭제
	 * 
	 * @param oldFile
	 * @param newFile
	 */
	public static void moveFile(String oldFile, String newFile) {
		if (copyFile(oldFile, newFile)) {
			File file = new File(oldFile);
			file.delete();
		}
	}

	/**
	 * 물리적 파일 복사
	 * 
	 * @param rFile
	 * @param wFile
	 * @return
	 */
	public static boolean copyFile(String rFile, String wFile) {
		boolean flag = true;
		FileOutputStream foStream = null;
		String saveDirectory = null;
		File dir = null;

		try {
			int iIndex = wFile.lastIndexOf("/");

			if (iIndex > 0) {
				saveDirectory = wFile.substring(0, (wFile.lastIndexOf("/") + 1));

				dir = new File(StringUtil.replace(saveDirectory, "//", "/"));

				if (!dir.isDirectory()) {
					dir.mkdirs();
				}
			}

			foStream = new FileOutputStream(wFile);
			flag = dumpFile(rFile, foStream);
			foStream.close();
		} catch (Exception ex) {
			try {
				if (foStream != null)
					foStream.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}

			ex.printStackTrace();

			flag = false;
		} finally {
			try {
				if (foStream != null)
					foStream.close();
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 물리적 파일 덤프
	 * 
	 * @param rFile
	 * @param outputstream
	 * @return
	 */
	public static boolean dumpFile(String rFile, OutputStream outputstream) {
		byte abyte0[] = new byte[4096];
		boolean flag = true;
		FileInputStream fiStream = null;
		try {
			fiStream = new FileInputStream(rFile);
			int i;
			while ((i = fiStream.read(abyte0)) != -1)
				outputstream.write(abyte0, 0, i);

			fiStream.close();
		} catch (Exception ex) {
			try {
				if (fiStream != null)
					fiStream.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			ex.printStackTrace();

			flag = false;
		} finally {
			try {
				if (fiStream != null)
					fiStream.close();
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
		}

		return flag;
	}

	/**
	 * 디렉토리 생성
	 * 
	 * @param strDir
	 * @return
	 */
	public static boolean createDirectory(String strDir) {
		File file = null;
		boolean bFlag = false;

		if (strDir != null && strDir.length() > 0) {
			file = new File(strDir);
			if (!file.isDirectory()) {
				bFlag = file.mkdirs();
			}
		}

		return bFlag;
	}

	/**
	 * MineType 체크
	 * 
	 * @param type
	 * @return
	 */
	public static boolean checkMineType(String type) {
		boolean bFlag = false;

		String[] arr = DEFAULT_MIMETYPE.split(",");
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(type)) {
				bFlag = true;
				break;
			}
		}

		return bFlag;
	}
}
