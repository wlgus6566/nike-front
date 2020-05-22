package com.nike.dnp.util;

/**
 * ObjectUtil
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description ObjectUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

public class ObjectUtil {

	/**
	 * Object NULL 체크
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNull(Object value) {
		return (value == null) ? true : false;
	}

	public static boolean isNotNull(Object value) {
		return (value == null) ? false : true;
	}

	/**
	 * Object[] NULL 체크
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNull(Object[] value) {
		return (value != null && value.length > 0) ? false : true;
	}

	public static boolean isNotNull(Object[] value) {
		return (value != null && value.length > 0) ? true : false;
	}

	/**
	 * Object > Object 형변환
	 * 
	 * @param value
	 * @return
	 */
	public static Object nvl(Object value) {
		return (isNull(value)) ? "" : (isNull(StringUtil.nvl(String.valueOf(value)))) ? "" : StringUtil.nvl(String.valueOf(value));
	}

}
