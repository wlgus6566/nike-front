package com.nike.dnp.common.variable;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ServiceEnumCode {

    /**
     * Y or N
     */
    public enum YesOrNoEnumCode {
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

    /**
     * The enum Product category 2 enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum ProductCategory2EnumCode {
        SUBSIDIARY("부자재"),
        NIKE_BY_YOU("NIKE BY YOU"),
        CUSTOM23("CUSTOM23"),
        MNQ("MNQ");

        /**
         * 코드값
         *
         * @author [윤태호]
         */
        private final String message;
    }

    /**
     * The enum Product category 3 enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum ProductCategory3EnumCode {
        SUBSIDIARY21("운영 비품"),
        SUBSIDIARY22("스태프 비품"),
        SUBSIDIARY23("운영 사이니지"),
        SUBSIDIARY24("세일 사이니지"),
        SUBSIDIARY25("오픈 패키지"),
        SUBSIDIARY26("나이키 골프"),
        NIKE_BY_YOU27("신발 커스텀 (단품)"),
        NIKE_BY_YOU28("신발 커스텀 (패키지)"),
        NIKE_BY_YOU29("의류 커스텀 (단품)"),
        NIKE_BY_YOU30("의류 커스텀 (패키지)"),
        NIKE_BY_YOU31("OTHERS"),
        CUSTOM2332("신발 커스텀 (단품)"),
        CUSTOM2333("신발 커스텀 (패키지)"),
        CUSTOM2334("의류 커스텀 (단품)"),
        CUSTOM2335("의류 커스텀 (패키지)"),
        CUSTOM2336("OTHERS"),
        MNQ37("남성"),
        MNQ38("여성"),
        MNQ39("유아동"),
        MNQ40("수리/보수");

        /**
         * 코드값
         *
         * @author [윤태호]
         */
        private final String message;
    }

    /**
     * The enum Menu skill enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum MenuSkillEnumCode {
        LIST("목록", "listAuthYn", 0)
        ,CREATE("등록/수정", "creationAuthYn", 1)
        ,DELETE("삭제", "deleteAuthYn", 2)
        ,VIEW("상세보기", "detailAuthYn", 3)
        ,DOWNLOAD("다운로드/주문", "downloadAuthYn", 4)
        ,REPORT("시공보고서", "reportAuthYn", 5)
        ;
        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String message;
        private final String field;
        private final int sort;
    }

    /**
     * The enum File folder enum code.
     */
    @RequiredArgsConstructor
    @Getter
    public enum FileFolderEnumCode {
        PRODUCT("product"),
        TEMP("temp");

        /**
         * 코드값
         *
         * @author [윤태호]
         */

        private final String folder;

    }
}
