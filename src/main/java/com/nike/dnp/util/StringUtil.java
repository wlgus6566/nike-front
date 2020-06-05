package com.nike.dnp.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 * String Util
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description StringUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

@Slf4j
@UtilityClass
public class StringUtil {

	public static final Pattern SCRIPT_ELEMENT = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", Pattern.DOTALL);
	public static final Pattern SCRIPT_ATTRIBUTE = Pattern.compile("javascript:.*", Pattern.DOTALL);

	/**
	 * String > String 공백제거
	 *
	 * @param value
	 * @return
	 */
	public static String nvl(String value) {
		return value != null && value.length() > 0 ? value.trim() : "";
	}

	/**
	 * Long > String 형변환
	 *
	 * @param value
	 * @return
	 */
	public static String nvl(long value) {
		return ObjectUtil.isNull(value) ? "" : Long.toString(value);
	}

	/**
	 * Integer > String 형변환
	 *
	 * @param value
	 * @return
	 */
	public static String nvl(int value) {
		return ObjectUtil.isNull(value) ? "" : Integer.toString(value);
	}

	/**
	 * Object > String 형변환
	 *
	 * @param value
	 * @return
	 */
	public static String nvl(Object value) {
		return ObjectUtil.isNull(value) ? "" : String.valueOf(value);
	}

	/**
	 * String > String 데이터
	 *
	 * @description 대체 value가 공백일 경우 repairValue로 대체
	 * @param value
	 * @param repairValue
	 * @return
	 */
	public static String nvl(String value, String repairValue) {
		return "".equals(nvl(value)) ? nvl(repairValue) : value;
	}

    /**
     * Object -> String 데이터
     * 
     * @description Object -> String 데이터 대체
     * @param value
     * @param repairValue
     * @return
     */
	public static String nvl(Object value, String repairValue) {
	    return nvl(nvl(value), repairValue);
    }

	/**
	 * String > Integer 데이터
	 *
	 * @description 대체 value가 공백일 경우 repairValue로 대체
	 * @param value
	 * @param repairValue
	 * @return
	 */
	public static String nvl(String value, int repairValue) {
		return "".equals(nvl(value)) ? nvl(repairValue) : value;
	}

	/**
	 * String > long 데이터
	 *
	 * @description 대체 value가 공백일 경우 repairValue로 대체
	 * @param value
	 * @param repairValue
	 * @return
	 */
	public static String nvl(String value, long repairValue) {
		return "".equals(nvl(value)) ? nvl(repairValue) : value;
	}

	/**
	 * 디코드
	 *
	 * @param value
	 * @return
	 */
	public static String decode(String value) {
		String returnValue = "";

		if (!value.isEmpty()) {
			try {
				returnValue = URLDecoder.decode(value, "utf-8");
			} catch (UnsupportedEncodingException exception) {
				log.error("###Exception Error###", exception);
			}
		}

		return returnValue;
	}

	/**
	 * 인코드
	 *
	 * @param value
	 * @return
	 */
	public static String encode(String value) {
		String returnValue = "";

		if (!value.isEmpty()) {
			try {
				returnValue = URLEncoder.encode(value, "utf-8");
			} catch (UnsupportedEncodingException exception) {
				log.error("###Exception Error###", exception);
			}
		}

		return returnValue;
	}

}