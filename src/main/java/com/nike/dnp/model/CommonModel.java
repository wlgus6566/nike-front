package com.nike.dnp.model;

import lombok.Data;

import java.util.Date;

/**
 * Common Model
 * 
 * @since 2020.05.21
 * @author [오지훈]
 * @Description Common Model 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

@Data
public class CommonModel {

	/**
	 * 프로세스구분 CREATE/MODIFY/DELETE
	 */
	private String viewType;

	/**
	 * 조건구분 ONE/ARRAY
	 */
	private String whereType;

	/**
	 * set구분
	 */
	private String setType;

	/**
	 * 되돌아갈 URL
	 */
	private String returnUrl;

	/**
	 * 사용_여부
	 */
	private String useYn;

	/**
	 * Banner - 정렬_번호
	 */
	private long sortingNo;

	/**
	 * 최초등록자 시퀀스
	 */
	private long registerSeq;

	/**
	 * 등록일시
	 */
	private Date registrationDt;

	/**
	 * 수정자 시퀀스
	 */
	private long updaterSeq;

	/**
	 * 수정일시
	 */
	private Date updateDt;

	/**
	 * 관리자 시퀀스
	 */
	private long managerSeq;

	/**
	 * 관리자 수정일시
	 */
	private Date managerUpdateDt;
	
}
