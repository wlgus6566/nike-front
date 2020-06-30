package com.nike.dnp.common.variable;

import org.springframework.stereotype.Component;

/**
 * EnumCode
 * <p>
 * 이 클래스는 각 서비스별 발생할 수 있는 코드 정의입니다.
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 11. 오후 3:52:38
 * @Description 서비스 별 EnumCode
 * @history [이소정] [2020.06.11] [최초 작성]
 * @since 2020.06.11
 */
@Component
public class EnumCode {

    /**
     * 정렬 순서
     *
     * @author [이소정]
     */
    public enum SearchEnumCode {

        /**
         * Latest search enum code
         *
         * @author [오지훈]
         */
        LATEST //최신순

        /**
         * Start date search enum code
         *
         * @author [오지훈]
         */
        ,START_DATE //시작일순
    }

    /**
     * 콘텐츠 > 캠페인기간 enumCode
     */
    public enum ContentsCampaignPeriodCode {
        SELECT  //날짜선택
        , EVERY  //365
    }

    /**
     * 콘텐츠 > 파일 > 파일구분
     */
    public enum ContentsFileSectionCode {
        ASSET
        , GUIDE
    }

    /**
     * 콘텐츠 > 파일 > 파일종류
     */
    public enum ContentsFileKindCode {
        FILE  // 파일
        , VIDEO  // 동영상(URL)
        , VR  // VR
    }
}
