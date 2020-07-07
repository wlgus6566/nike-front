package com.nike.dnp.common.variable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * The Class Service enum code.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 3. 오전 11:34:38
 * @Description
 */
@Component
public class ServiceEnumCode {

    /**
     * Y or N
     */
    public enum yesOrNoEnumCode {
        Y,N
    }

    /**
     * 정렬 순서
     *
     * @author [이소정]
     */
    public enum SearchEnumCode {
        LATEST //최신순
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
        , VIDEO
    }

    /**
     * 콘텐츠 > 파일 > 파일종류
     */
    public enum ContentsFileKindCode {
        FILE  // 파일
        , VIDEO  // 동영상(URL)
        , VR  // VR
    }

    /**
     * The enum User status enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum UserStatusEnumCode {

        NORMAL("정상"),
        DORMANT("휴면"),
        DELETE("탈퇴");

        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String message;
    }

    /**
     * The enum Email type.
     */
    @RequiredArgsConstructor
    @Getter
    public enum EmailTypeEnumCode {
        USER_CREATE("[NIKE/TEST] NIKE D&P PLATFORM 계정 생성 안내"),
        PASSWORD_SETTING("[NIKE/TEST] NIKE D&P PLATFORM 비밀번호 설정 안내"),
        PASSWORD_GUIDE("[NIKE/TEST] NIKE D&P PLATFORM 비밀번호 변경 안내"),
        CERT_CODE_SEND("[NIKE/TEST] NIKE D&P PLATFORM E-MAIL 인증 안내"),
        DORMANT_PREV("[NIKE/TEST] NIKE D&P PLATFORM 휴면계정 전환 사전 안내"),
        DORMANT_ACTIVE("[NIKE/TEST] NIKE D&P PLATFORM 휴면계정 처리 안내"),
        DORMANT_CHANGE("[NIKE/TEST] NIKE D&P PLATFORM 휴면계정 해제 안내"),
        CONTENTS_UPDATE("[NIKE/TEST] NIKE D&P PLATFORM 컨텐츠 업데이트 알림"),
        ORDER("[NIKE/TEST] NIKE D&P PLATFORM 주문서");

        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String message;
    }

    /**
     * The enum Return type enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum ReturnTypeEnumCode {
        CREATE("등록되었습니다.")
        ,UPDATE("수정되었습니다.")
        ,DELETE("삭제되었습니다.")
        ,CANCEL("취소되었습니다.")
        ;
        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String message;
    }

    @RequiredArgsConstructor
    @Getter
    public enum MenuSkillEnumCode {
        LIST("목록")
        ,CERATE("등록/수정")
        ,DELETE("삭제")

        ,VIEW("상세보기")
        ,DOWNLOAD("다운로드/주문")
        ,REPORT("시공보고서")
        ;
        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String message;
    }
}
