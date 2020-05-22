package com.nike.dnp.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Response Util
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description ResponseUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

public class ResponseUtil {

	// /**
	// * 성공 메세지 공통 처리
	// *
	// * @param data - 반환 데이터
	// * @param criteria - 조건 파라미터
	// * @return ResultRestData - 결과 데이터 응답 클래스
	// */
	// public static ResultRestData makeSuccessResult(Object data, Criteria
	// criteria) {
	// ResultRestData result = new ResultRestData();
	//
	// result.setResult("SUCCESS");
	// result.setData(data);
	// result.setCriteria(criteria);
	//
	// return result;
	// }

	/**
	 * 성공 메세지 공통 처리
	 * 
	 * @param data     - 반환 데이터
	 * @param criteria - 조건 파라미터
	 * @return ResultRestData - 결과 데이터 응답 클래스
	 */
	public static ResultRestData makeSuccessResult(Object data) {
		ResultRestData result = new ResultRestData();

		result.setResult("SUCCESS");
		result.setData(data);

		return result;
	}

	/**
	 * 에러 메세지 공통 처리
	 * 
	 * @param code           - 에러코드
	 * @param defaultMessage - 에러 메세지
	 * @return ResultRestData - 결과 데이터 응답 클래스
	 */
	public static ResultRestData makeErrorResult(String code, String defaultMessage) {
		ResultRestData result = new ResultRestData();
		Map<String, Object> errorMap = new HashMap<String, Object>();

		result.setResult("ERROR");

		errorMap.put("message", defaultMessage);
		errorMap.put("type", code);
		result.setData(errorMap);

		return result;
	}

	/**
	 * 에러 메세지 공통 처리
	 * 
	 * @param code           - 에러코드
	 * @param defaultMessage - 에러 메세지
	 * @return ResultRestData - 결과 데이터 응답 클래스
	 */
	public static ResultRestData makeErrorResult(String code, String defaultTitleMessage, String defaultMessage) {
		ResultRestData result = new ResultRestData();
		Map<String, Object> errorMap = new HashMap<String, Object>();

		result.setResult("ERROR");

		errorMap.put("title", defaultTitleMessage);
		errorMap.put("message", defaultMessage);
		errorMap.put("type", code);
		result.setData(errorMap);

		return result;
	}

	/**
	 * 에러 메세지 공통 처리
	 * 
	 * @param code           - 에러코드
	 * @param defaultMessage - 에러 메세지
	 * @return ResultRestData - 결과 데이터 응답 클래스
	 */
	public static ResultRestData makeErrorResult() {
		ResultRestData result = new ResultRestData();
		Map<String, Object> errorMap = new HashMap<String, Object>();

		result.setResult("ERROR");

		errorMap.put("message", "장애가 발생했습니다.관리자에 문의 바랍니다.");
		errorMap.put("type", "ERROR");
		result.setData(errorMap);

		return result;
	}

	/**
	 * 성공 메세지 공통 처리
	 * 
	 * @param data     - 반환 데이터
	 * @param criteria - 조건 파라미터
	 * @return ResultRestData - 결과 데이터 응답 클래스
	 */
	public static Map<String, Object> makeSuccessResultToMap(Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("result", "SUCCESS");
		resultMap.put("data", data);

		return resultMap;
	}

	/**
	 * 성공 메시지 공통 처리
	 * 
	 * @param data
	 * @param dataName
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> makeSuccessResultToMap(Object data, String dataName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "SUCCESS");
		resultMap.put(dataName, data);

		return resultMap;
	}

	/**
	 * 에러 메세지 공통 처리
	 * 
	 * @param code           - 에러코드
	 * @param defaultMessage - 에러 메세지
	 * @return ResultRestData - 결과 데이터 응답 클래스
	 */
	public static Map<String, Object> makeErrorResultToMap(String code, String defaultMessage) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> errorMap = new HashMap<String, Object>();

		resultMap.put("result", "ERROR");

		errorMap.put("message", defaultMessage);
		errorMap.put("type", code);

		resultMap.put("data", errorMap);

		return resultMap;
	}

	public static Map<String, Object> makeErrorResultToMap(String code, String title, String defaultMessage, String dataName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> errorMap = new HashMap<String, Object>();

		resultMap.put("result", "ERROR");

		errorMap.put("title", title);
		errorMap.put("message", defaultMessage);
		errorMap.put("type", code);

		resultMap.put(dataName, errorMap);

		return resultMap;
	}

	/**
	 * 에러 메세지 공통 처리
	 * 
	 * @param code           - 에러코드
	 * @param defaultMessage - 에러 메세지
	 * @return ResultRestData - 결과 데이터 응답 클래스
	 */
	public static Map<String, Object> makeErrorResultToMap() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> errorMap = new HashMap<String, Object>();

		resultMap.put("result", "ERROR");

		errorMap.put("message", "장애가 발생했습니다.관리자에 문의 바랍니다.");
		errorMap.put("type", "ERROR");

		resultMap.put("data", errorMap);

		return resultMap;
	}

	////////////////////////////////////////////////////
	/**
	 * 성공 시
	 * 
	 * @param data
	 * @return
	 */
	public static ResultRestDialog makeSuccessDialog(Object data) {
		ResultRestDialog result = new ResultRestDialog();
		result.setResult("SUCCESS");
		result.setDialog(data);

		return result;
	}

	public static ResultRestDialog makeSuccessDialog(String code, String title, String message) {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("type", code);
		errorMap.put("title", title);
		errorMap.put("message", message);
		errorMap.put("btntitle", "확인");
		errorMap.put("effect", "NONE"); // "NONE"

		ResultRestDialog result = new ResultRestDialog();
		result.setResult("SUCCESS");
		result.setDialog(errorMap);

		return result;
	}

	public static ResultRestDialog makeFailDialog(String code, String title, String message, Object data) {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("type", code);
		errorMap.put("title", title);
		errorMap.put("message", message);
		errorMap.put("data", data);
		errorMap.put("btntitle", "확인");
		errorMap.put("effect", "NONE"); // "NONE"

		ResultRestDialog result = new ResultRestDialog();
		result.setResult("SUCCESS");
		result.setDialog(errorMap);

		return result;
	}

	public static ResultRestDialog makeFailDialog(String code, String title, String message, String btntitle, String effect) {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("type", code);
		errorMap.put("title", title);
		errorMap.put("message", message);
		errorMap.put("btntitle", btntitle);
		errorMap.put("effect", effect); // "NONE"

		ResultRestDialog result = new ResultRestDialog();
		result.setResult("SUCCESS");
		result.setDialog(errorMap);

		return result;
	}

	public static ResultRestDialog makeFailDialog(String code, String title, String message) {
		return makeFailDialog(code, title, message, "확인", "NONE");
	}

	public static ResultRestDialog makeErrorDialog(String code, String title, String message) {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("type", code);
		errorMap.put("title", title);
		errorMap.put("message", message);
		errorMap.put("btntitle", "확인");
		errorMap.put("effect", "NONE"); // "NONE"

		ResultRestDialog result = new ResultRestDialog();
		result.setResult("ERROR");
		result.setDialog(errorMap);

		return result;
	}

	/**
	 * 성공 메세지 공통 처리
	 * 
	 * @param data     - 반환 데이터
	 * @param criteria - 조건 파라미터
	 * @return ResultRestData - 결과 데이터 응답 클래스
	 */
	public static ResultRestData makeSuccessData(Object data) {
		ResultRestData result = new ResultRestData();

		result.setResult("SUCCESS");
		result.setData(data);

		return result;
	}

	/**
	 * 성공 메세지 공통 처리 - 다이얼로그 없음
	 * 
	 * @param data     - 반환 데이터
	 * @param criteria - 조건 파라미터
	 * @return ResultRestData - 결과 데이터 응답 클래스
	 */
	public static ResultRestData makeSuccessData(Map<String, Object> data) {
		return makeSuccessData(data, null);
	}

	/**
	 * 성공 메세지 공통 처리 - 다이얼로그 있음
	 * 
	 * @param data     - 반환 데이터
	 * @param criteria - 조건 파라미터
	 * @return ResultRestData - 결과 데이터 응답 클래스
	 */
	public static ResultRestData makeSuccessData(Map<String, Object> data, Map<String, Object> dialog) {
		ResultRestData result = new ResultRestData();
		if (dialog != null) {
			data.put("dialog", dialog);
		}
		result.setResult("SUCCESS");
		result.setData(data);

		return result;
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static ResultRestData makeFailData(Map<String, Object> data) {
		return makeFailData(data, null);
	}

	public static ResultRestData makeFailData(Map<String, Object> data, Map<String, Object> dialog) {
		ResultRestData result = new ResultRestData();
		if (dialog != null) {
			data.put("dialog", dialog);
		}
		result.setResult("SUCCESS");
		result.setData(data);

		return result;
	}

}
