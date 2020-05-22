package com.nike.dnp.util;

import java.util.Random;

/**
 * NumberUtil
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description NumberUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

public class NumberUtil {

	/**
	 * int null check
	 * 
	 * @param value
	 * @return
	 */
	public static int nvl(int value) {
		return ("".equals(StringUtil.nvl(String.valueOf(value)))) ? 0 : value;
	}

	/**
	 * int > int 데이터 대체
	 * 
	 * @description 대체 value가 0일 경우 repairValue로 대체
	 * @param value
	 * @param repairValue
	 * @return
	 */
	public static int nvl(int value, int repairValue) {
		return (nvl(value) == 0) ? nvl(repairValue) : value;
	}

	/**
	 * long > int 데이터 대체
	 * 
	 * @description 대체 value가 0일 경우 repairValue로 대체
	 * @param value
	 * @param repairValue
	 * @return
	 */
	public static int nvl(long value, int repairValue) {
		return (nvl(value) == 0) ? nvl(repairValue) : (int) value;
	}

	/**
	 * String > int 데이터 대체
	 * 
	 * @description 대체 value가 0일 경우 repairValue로 대체
	 * @param value
	 * @param repairValue
	 * @return
	 */
	public static int nvl(String value, int repairValue) {
		return ("".equals(StringUtil.nvl(value))) ? NumberUtil.nvl(repairValue) : Integer.parseInt(value);
	}

	/**
	 * long null check
	 * 
	 * @param value
	 * @return
	 */
	public static long nvl(long value) {
		return ("".equals(StringUtil.nvl(String.valueOf(value)))) ? 0 : value;
	}

	/**
	 * long > long 데이터 대체
	 * 
	 * @description 대체 value가 0일 경우 repairValue로 대체
	 * @param value
	 * @param repairValue
	 * @return
	 */
	public static long nvl(long value, long repairValue) {
		return (nvl(value) == 0) ? nvl(repairValue) : value;
	}

	/**
	 * int > long 데이터 대체
	 * 
	 * @description 대체 value가 0일 경우 repairValue로 대체
	 * @param value
	 * @param repairValue
	 * @return
	 */
	public static long nvl(int value, long repairValue) {
		return (nvl(value) == 0) ? nvl(repairValue) : (long) value;
	}

	/**
	 * String > long 데이터 대체
	 * 
	 * @description 대체 value가 0일 경우 repairValue로 대체
	 * @param value
	 * @return
	 */
	public static long nvl(String value, long repairValue) {
		return ("".equals(StringUtil.nvl(value))) ? NumberUtil.nvl(repairValue) : Long.parseLong(value);
	}

	public static long getSize(long[] value) {
		return (ObjectUtil.isNull(value)) ? 0 : value.length;
	}

	/**
	 * 임의 숫자
	 * 
	 * @param length(자리수)
	 * @return
	 */
	public static int random(int length) {
		String maximum = "1";
		String minimum = "1";

		for (int i = 0; i < length; i++) {
			maximum += "0";
			minimum += (i < length - 1) ? "0" : "";
		}

		Random random = new Random();
		int result = random.nextInt(Integer.parseInt(maximum)) + Integer.parseInt(minimum);

		if (result > Integer.parseInt(maximum)) {
			result -= Integer.parseInt(minimum);
		}

		return result;
	}

}
