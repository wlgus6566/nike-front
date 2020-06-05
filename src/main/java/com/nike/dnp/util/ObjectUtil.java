package com.nike.dnp.util;

import lombok.experimental.UtilityClass;

/**
 * ObjectUtil
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description ObjectUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */
@UtilityClass
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

}
