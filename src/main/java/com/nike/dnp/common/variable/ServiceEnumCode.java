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
public class ServiceEnumCode {

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
        , VIDEO
    }

    /**
     * 콘텐츠 > 파일 > 파일종류
     */
    public enum ContentsFileKindCode {
        FILE  // 파일
        ,
        /**
         * Video contents file kind code
         *
         * @author [오지훈]
         */
        VIDEO  // 동영상(URL)
        ,
        /**
         * Vr contents file kind code
         *
         * @author [오지훈]
         */
        VR  // VR
    }

    /**
     * The enum User status enum code.
     */
    public enum UserStatusEnumCode {

        /**
         * Normal user enum code
         *
         * @author [오지훈]
         */
        NORMAL("정상"),
        /**
         * Dormant user enum code
         *
         * @author [오지훈]
         */
        DORMANT("휴면"),
        /**
         * Out user enum code
         *
         * @author [오지훈]
         */
        OUT("탈퇴");

        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String value;

        /**
         * Instantiates a new User status enum code.
         *
         * @param value - 코드값
         * @author [오지훈]
         * @CreatedOn 2020. 6. 23. 오후 5:39:17
         * @Description 생성자
         */
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
    public enum EmailType {
        /**
         * 계정 생성 알림 메일
         *
         * @author [오지훈]
         */
        USER_CREATE("[NIKE/TEST] NIKE D&P PLATFORM 계정 생성 안내"),

        /**
         * 비밀번호 설정 알림 메일
         *
         * @author [오지훈]
         */
        PASSWORD_SETTING(""),

        /**
         * 비밀번호 변경 안내 메일
         *
         * @author [오지훈]
         */
        PASSWORD_GUIDE(""),

        /**
         * 인증코드 전송 메일
         *
         * @author [오지훈]
         */
        CERT_CODE_SEND(""),

        /**
         * 휴면회원 사전 알림 메일
         *
         * @author [오지훈]
         */
        DORMANT_PREV(""),

        /**
         * 휴면회원 변경 알림 메일
         *
         * @author [오지훈]
         */
        DORMANT_CHANGE(""),

        /**
         * 휴면상태 활성화 완료 메일
         *
         * @author [오지훈]
         */
        DORMANT_ACTIVE(""),

        /**
         * 컨텐츠 업데이트 알림 메일
         *
         * @author [오지훈]
         */
        CONTENTS_UPDATE(""),

        /**
         * 주문서 메일
         *
         * @author [오지훈]
         */
        ORDER("[NIKE/TEST] NIKE D&P PLATFORM 주문서");

        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String value;

        /**
         * Instantiates a new User status enum code.
         *
         * @param value - 코드값
         * @author [오지훈]
         * @CreatedOn 2020. 6. 23. 오후 5:39:17
         * @Description 생성자
         */
        EmailType(final String value) {
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
