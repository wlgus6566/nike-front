package com.nike.dnp.common.variable;

import org.springframework.stereotype.Component;


/**
 * The Class Success enum code.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 1. 오후 5:01:10
 * @Description
 */
@Component
public class SuccessEnumCode {

    /**
     * The enum Auth success enum.
     */
    public enum AuthSuccessEnum {

        NO_AUTH("접근 권한이 없습니다.");

        /**
         * The Value
         * @author [이소정]
         */
        private final String value;

        AuthSuccessEnum(final String value) {
            this.value = value;
        }

        /**
         * Gets message.
         *
         * @return the message
         * @author [이소정]
         * @CreatedOn 2020. 7. 1. 오후 2:46:38
         * @Description
         */
        public String getMessage() {
            return value;
        }
    }

    /**
     * The enum Login success enum.
     */
    public enum LoginSuccessEnum {

        CHECK_EMAIL("E-MAIL을 다시 확인 후 입력해 주세요"),
        SEND_EMAIL_CODE("해당 계정의 E-MAIL로 인증코드가 발송되었습니다. 이메일 확인 후 인증코드를 입력해 주세요"),
        SEND_EMAIL("E-MAIL이 발송되었습니다. E-MAIL을 확인주세요"),
        CHANGE_PW("설정한 비밀번호로 로그인 해 주세요.");

        /**
         * The Value
         * @author [이소정]
         */
        private final String value;

        LoginSuccessEnum(final String value) {
            this.value = value;
        }

        /**
         * Gets message.
         *
         * @return the message
         * @author [이소정]
         * @CreatedOn 2020. 7. 1. 오후 4:00:44
         * @Description
         */
        public String getMessage() {
            return value;
        }
    }

    /**
     * The enum User success.
     */
    public enum UserSuccess {
        NOT_DUPLICATE("사용 가능한 ID입니다.");

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
        UserSuccess(final String value) {
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
     * The enum Order success enum.
     */
    public enum OrderSuccessEnum {
        ADDED_WISHLIST("위시리스트에 추가 되었습니다. 위시리스트는 마이페이지에서 확인가능합니다"),
        ADDED_CART("CART에 담겼습니다"),
        SEND_ORDER_MAIL("주문메일이 발송 되었습니다.");

        /**
         * The Value
         * @author [이소정]
         */
        private final String value;

        OrderSuccessEnum(final String value) {
            this.value = value;
        }

        /**
         * Gets message.
         *
         * @return the message
         * @author [이소정]
         * @CreatedOn 2020. 7. 1. 오후 4:59:31
         * @Description
         */
        public String getMessage() {
            return value;
        }
    }

    /**
     * The enum Report success enum.
     */
    public enum ReportSuccessEnum {
        SAVED_REPORT("등록 되었습니다.");

        /**
         * The Value
         * @author [이소정]
         */
        private final String value;

        ReportSuccessEnum(final String value) {
            this.value = value;
        }

        /**
         * Gets message.
         *
         * @return the message
         * @author [이소정]
         * @CreatedOn 2020. 7. 1. 오후 4:59:21
         * @Description
         */
        public String getMessage() {
            return value;
        }
    }

}
