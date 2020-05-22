package com.nike.dnp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
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
		return (value != null && value.length() > 0) ? value.trim() : "";
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
		return ("".equals(nvl(value))) ? nvl(repairValue) : value;
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
		return ("".equals(nvl(value))) ? nvl(repairValue) : value;
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
		return ("".equals(nvl(value))) ? nvl(repairValue) : value;
	}

	/**
	 * 문자열 치환
	 *
	 * @param value
	 * @param pattern
	 * @param replace
	 * @return
	 */
	public static String replace(String value, String pattern, String replace) {
		if (!ObjectUtil.isNull(value)) {
			final int length = pattern.length();
			StringBuffer stringBuffer = new StringBuffer();
			int found = -1;
			int start = 0;

			while ((found = value.indexOf(pattern, start)) != -1) {
				stringBuffer.append(value, start, found);
				stringBuffer.append(replace);
				start = found + length;
			}

			stringBuffer.append(value.substring(start));
			return stringBuffer.toString();
		}

		return "";
	}

	/**
	 * 디코드
	 *
	 * @param value
	 * @return
	 */
	public static String decode(String value) {
		try {
			value = URLDecoder.decode(value, "utf-8");
		} catch (Exception e) {
			System.out.println("StringUtil.decode : " + e);
		}

		return value;
	}

	/**
	 * 인코드
	 *
	 * @param value
	 * @return
	 */
	public static String encode(String value) {
		try {
			value = URLEncoder.encode(value, "utf-8");
		} catch (Exception e) {
			System.out.println("StringUtil.encode : " + e);
		}

		return value;
	}

	/**
	 * 마크업 작성 form
	 *
	 * @param parameterClass
	 * @param parameterObject
	 * @return
	 */
	public static String markupForm(Class<?> parameterClass, Object parameterObject) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		StringBuffer stringBuffer = new StringBuffer();

		Class<?> variableClass = parameterClass;
		while (variableClass != null && variableClass != Object.class) {

			for (Field field : variableClass.getDeclaredFields()) {
				if (!field.isSynthetic()) {
					char[] arrayChar = field.getName().substring(0, 1).toCharArray();
					String fieldName = "get" + Character.toUpperCase(arrayChar[0]) + field.getName().substring(1);
					Object fieldValue;

					try {
						fieldValue = variableClass.getDeclaredMethod(fieldName).invoke(parameterObject);
						hashMap.put(field.getName(), StringUtil.nvl(fieldValue));

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
			variableClass = variableClass.getSuperclass();
		}

		Iterator<String> iterator = hashMap.keySet().iterator();

		while (iterator.hasNext()) {
			String key = String.valueOf(iterator.next());
			String value = String.valueOf(hashMap.get(key));
			stringBuffer.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key + "\" value=\"" + value + "\" />\n");
		}

		return stringBuffer.toString();
	}

	/**
	 * 마크업 스크립트 제거
	 *
	 * @param value
	 * @return
	 */
	public static String removeScript(String value) {
		try {
			Matcher matcher;

			matcher = SCRIPT_ELEMENT.matcher(value);
			value = matcher.replaceAll("");
			matcher = SCRIPT_ATTRIBUTE.matcher(value);
			value = matcher.replaceAll("");
			value = value.replaceAll("(?i)<script", "");
			value = value.replaceAll("(?i)script>", "");

		} catch (Exception e) {
			value = "";
		}

		return value;
	}

	/**
	 * 앞에 주어진 숫자만큼의 자릿수로 모자랄 경우 앞에 0을 붙여 리턴한다.
	 *
	 * @param value
	 * @param size
	 * @return
	 */
	public static String paddingZero(String value, int size) {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < size; i++) {
			stringBuilder.append("0");
		}

		return new DecimalFormat(stringBuilder.toString()).format(Long.parseLong(value));
	}

	/**
	 * 앞에 주어진 숫자만큼의 자릿수로 모자랄 경우 앞에 0을 붙여 리턴한다.
	 *
	 * @param value
	 * @param size
	 * @return
	 */
	public static String paddingZero(int value,
							  int size) {
		return paddingZero(String.valueOf(value), size);
	}

	/**
	 * 세 자리수 마다 콤마를 추가해서 반환
	 *
	 * @param value
	 * @return
	 */
	public static String comma(long value) {
		return separate(String.valueOf(value), 3, ",");
	}

	/**
	 * value 를 length 자리마다 separator 를 추가해서 반환
	 *
	 * @param value
	 * @param length
	 * @param separator
	 * @return
	 */
	public static String separate(String value, int length, String separator) {
		StringBuffer reverseValue = new StringBuffer(value).reverse();
		int reverseLength = reverseValue.toString().length();

		StringBuffer stringBuffer = new StringBuffer();

		for (int i = 0; i < reverseLength; i++) {
			if (i % length == 0 && i != 0) {
				stringBuffer.append(separator);
			}

			stringBuffer.append(reverseValue.charAt(i));
		}

		return stringBuffer.reverse().toString();
	}

	/**
	 * 한글 문장뒤에 조사 (을,를) ex : 코카-콜라 핸드백'을', 코카-콜라 티셔츠'를'
	 *
	 * @param name
	 * @param firstValue
	 * @param secondValue
	 * @return
	 */
	public static String koreanPostposition(String name, String firstValue, String secondValue) {
		char lastName = name.charAt(name.length() - 1);

		// 한글의 제일 처음과 끝의 범위밖일 경우는 오류
		if (lastName < 0xAC00 || lastName > 0xD7A3) {
			return name;
		}

		String selectedValue = (lastName - 0xAC00) % 28 > 0 ? firstValue : secondValue;

		return name + selectedValue;
	}

	/**
	 * 문자열 Null Check
	 *
	 * <p>
	 * org.springframework.util.StringUtils isEmpty() 이용
	 *
	 * @param str - 대상 문자열
	 * @return 대상 문자열이 null이거나 null String일 경우 {@code true}
	 */
	public static boolean isNull(String str) {
		return StringUtils.isEmpty(str);
	}

	/**
	 * 문자열 Null Check
	 *
	 * <p>
	 * org.springframework.util.StringUtils isEmpty() 이용
	 *
	 * @param str - 대상 문자열
	 * @return 대상 문자열이 null이 아니고 null String도 아닐 경우 {@code true}
	 */
	public static boolean isNotNull(String str) {
		return !isNull(str);
	}

	/**
	 * 로그인 회원가입 유효성 체크
	 *
	 * <p>
	 * 계정@aa.aa 형식 체크
	 *
	 *
	 * @param str - email 주소
	 * @return 대상 문자열이 정상일 경우 {@code true}
	 */
	public static boolean isEmail(String str) {
		// @ 포함여부 체크
		if (!str.contains("@")) {
			return false;
		}

		String regex = "[\\S]+\\@[\\S]+\\.[a-zA-Z]";
		Matcher emailMatcher = Pattern.compile(regex).matcher(str);

		return emailMatcher.find();
	}

	/**
	 * 공백 체크
	 *
	 * @param str - 대상 문자열
	 * @return boolean - 대상 문자열에 공백이 있을 경우 {@code true}
	 */
	public static boolean isSpace(String str) {
		return Pattern.compile("\\s").matcher(str).find();
	}

	/**
	 * 길이 체크
	 *
	 * @param str    - 대상 문자열
	 * @param length - 제한 길이(Byte)
	 * @return boolean - 대상 문자열이 정상일 경우 {@code true}
	 */
	public static boolean isCheckLength(String str, int length) {
		return str.getBytes().length < length;

	}

	/**
	 * 특수문자 체크
	 *
	 * @param str - 대상 문자열
	 * @return boolean - 대상 문자열이 정상일 경우 {@code true}
	 */
	public static boolean isContainsSpecialChar(String str) {
		return Pattern.matches("^[ㄱ-ㅎ가-힣a-zA-Z0-9]*$", str);
	}

	/**
	 * 비밀번호 체크 (10~20자의 영문, 숫자)
	 *
	 * <p>
	 * 특수문자 포함여부 체크 && 비밀번호 체크
	 *
	 * @param str - 대상 문자열
	 * @return boolean - 대상 문자열이 정상일 경우 {@code true}
	 */
	public static boolean isPassword(String str) {
		boolean isPassowrd = Pattern.compile("(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{10,20}").matcher(str).find();
		return isContainsSpecialChar(str) && isPassowrd;
	}

	/**
	 * 닉네임 체크 (3~8자의 한글, 영문, 숫자)
	 *
	 * <p>
	 * 특수문자 포함여부 체크 && 닉네임 체크
	 *
	 * @param str - 대상 문자열
	 * @return boolean - 대상 문자열이 정상일 경우 {@code true}
	 */
	public static boolean isNickName(String str) {
		return Pattern.matches("^[ㄱ-ㅎ가-힣A-Za-z0-9]{3,8}$", str);
	}

	/**
	 * Map을 JSON String으로 변경
	 *
	 * @param jsonMap - 대상 맵
	 * @return String JSON String
	 */
	public static String mapToJsonString(Map<String, Object> jsonMap) {
		Gson gson = new GsonBuilder().create();
		String result = gson.toJson(jsonMap);
		return result;
	}

	/**
	 * 도메인 정보 획득
	 *
	 * @param request
	 * @return
	 */
	public static String getDomain(HttpServletRequest request) {
		int port = request.getServerPort();
		String domain = request.getServerName();
		if (port != 80 && port != 443) {
			domain += ":" + port;
		}
		return domain;
	}

	/**
	 * 임시비밀번호 생성
	 * <p>
	 * 영문 소문자 + 숫자 10자리 숫자
	 *
	 * @return String 임시비밀번호
	 */
	public static String randomPassword() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
	}

	/**
	 * 이메일 마스킹 처리
	 * <p>
	 * 3자리 초과 뒤에서 3자리만
	 * <p>
	 * 3자리 이하 전체 마스킹처리
	 *
	 * @param email - 이메일 주소
	 * @return String - 마스킹한 이메일 주소
	 */
	public static String maskingEmail(String email) {
		if (email.contains("@")) {
			String id = email.substring(0, email.indexOf("@"));
			String domain = email.substring(email.indexOf("@"));

			char[] ch = null;
			if (id.length() > 3) {
				return id.substring(0, id.length() - 3) + "***" + domain;
			} else {
				ch = new char[id.length()];
				Arrays.fill(ch, '*');
				return id.replace(id, String.valueOf(ch)) + domain;
			}
		} else {
			return email;
		}
	}

	/**
	 * 휴대폰 번호 가운데 자리 마스킹 처리
	 *
	 * @param telNo - 휴대폰 번호
	 * @return String - 마스킹한 휴대폰 번호
	 */
	public static String maskingTelNo(String telNo) {
		String regex = "(\\d{2,3})(\\d{3,4})(\\d{4})$";
		Matcher matcher = Pattern.compile(regex).matcher(telNo);
		if (matcher.find()) {
			String replaceTarget = matcher.group(2);
			char[] ch = new char[replaceTarget.length()];
			Arrays.fill(ch, '*');
			return telNo.replace(replaceTarget, String.valueOf(ch));
		} else {
			return telNo;
		}
	}

	/**
	 * 지정한 숫자만큼 뒤에서부터  자르기
	 *
	 * @param value
	 * @param addStr
	 * @param cutNum
	 * @return
	 */
	public static String strCut(String value,String addStr,int cutNum) {
		String formatStr = addStr+value;
		return formatStr.substring(formatStr.length()-cutNum);
	}

	/**
	 * 숫자 문자변환
	 *
	 * @param num
	 * @return
	 */
	public static String parse(Object num) {
		return String.valueOf(num);
	}
	
	/**
	 * 길이에 따라 "-" 문자 추가
	 *
	 * @param str
	 * @return
	 */
	public static String putDash(String str) {
		
		if(ObjectUtil.isNull(str)) {
			return "";
		}
		//년도
		if(str.length() == 8){
			return str.substring(0,4) + "-" + str.substring(4,6) + "-" + str.substring(6,8);
		}
		//휴대전화
		if(str.length() == 11){
			return str.substring(0,3) + "-" + str.substring(3,7) + "-" + str.substring(7,11);
		}
		return str;
	}

	/**
	 * Object > JsonString 변환
	 * @param object
	 * @return
	 */
	public static String objectToJsonString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			return "";
		}
	}

}