package com.nike.dnp.common.variable;

import org.springframework.stereotype.Component;

/**
 * The Class Service enum code.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 1. 오후 5:16:52
 * @Description
 */
@Component
public class ServiceEnumCode {

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
    public enum UserStatusEnumCode {

        NORMAL("정상"),
        DORMANT("휴면"),
        OUT("탈퇴");

        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String value;

        UserStatusEnumCode(final String value) {
            this.value = value;
        }

        /**
         * Gets message.
         *
         * @return value - 코드값
         * @author [오지훈]
         * @CreatedOn 2020. 6. 23. 오후 5:39:17
         * @Description 코드값
         */
        public String getMessage() {
            return value;
        }

    }

    /**
     * The enum Email type.
     */
    public enum EmailTypeEnumCode {
        USER_CREATE("[NIKE/TEST] NIKE D&P PLATFORM 계정 생성 안내"),
        PASSWORD_SETTING(""),
        PASSWORD_GUIDE(""),
        CERT_CODE_SEND(""),
        DORMANT_PREV(""),
        DORMANT_CHANGE(""),
        DORMANT_ACTIVE(""),
        CONTENTS_UPDATE(""),
        ORDER("[NIKE/TEST] NIKE D&P PLATFORM 주문서");

        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String value;

        EmailTypeEnumCode(final String value) {
            this.value = value;
        }

        /**
         * Gets message.
         *
         * @return value - 코드값
         * @author [오지훈]
         * @CreatedOn 2020. 6. 23. 오후 5:39:17
         * @Description 코드값
         */
        public String getMessage() {
            return value;
        }
    }
}
