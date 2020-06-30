package com.nike.dnp.common.variable;

import org.springframework.stereotype.Component;

/**
 * ErrorEnumCode<br><br>
 * <p>
 * 이 클래스는 각 서비스별 발생할 수 있는 에러 코드와 메세지 정의입니다.<br>
 * 에러코드 작성 규칙은 해당 <b>서비스명 앞에서부터 3자 + E + 숫자</b> 입니다.<br>
 * ex) Login 서비스의 에러 코드 : LOG + E + 01 => LOGE01
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 24. 오후 5:25:57
 * @Description 서비스 별 에러 EnumCode
 * @history [이소정] [2020.06.03] [최초 작성]
 * @since 2020.06.03
 */
@Component
public class ErrorEnumCode {

    /**
     * The enum Common error.
     *
     * @author [이소정]
     */
    public enum CommonError {
        /**
         * Come 01 common error
         *
         * @author [오지훈]
         */
        COME01("로그인이 필요합니다."),

        /**
         * Come 02 common error
         *
         * @author [오지훈]
         */
        COME02("권한이 없습니다."),

        /**
         * Come 03 common error
         *
         * @author [오지훈]
         */
        COME03("데이터가 존재하지 않습니다.");

        /**
         * 에러 메시지
         *
         * @author [이소정]
         */
        private final String value;

        /**
         * 생성자
         *
         * @param value - 에러 메시지
         * @author [이소정]
         * @CreatedOn 2020. 6. 24. 오후 5:25:57
         * @Description
         */
        CommonError(final String value) {
            this.value = value;
        }

        /**
         * 에러 메시지 조회
         *
         * @return value - 에러 메시지
         * @author [이소정]
         * @CreatedOn 2020. 6. 24. 오후 5:25:57
         * @Description
         */
        public String getMessage() {
            return value;
        }
    }


    /**
     * The enum Login error.
     *
     * @author [이소정]
     */
    public enum LoginError {

        /**
         * Loge 01 login error
         *
         * @author [오지훈]
         */
        LOGE01("가입여부를 확인해주세요.\n등록된 정보가 없습니다."),
        /**
         * Loge 02 login error
         *
         * @author [오지훈]
         */
        LOGE02("이메일을 정확히 입력해주세요."),
        /**
         * Loge 03 login error
         *
         * @author [오지훈]
         */
        LOGE03("비밀번호를 입력해주세요."),
        /**
         * Loge 04 login error
         *
         * @author [오지훈]
         */
        LOGE04("이미 사용중인 이메일 입니다."),
        /**
         * Loge 05 login error
         *
         * @author [오지훈]
         */
        LOGE05("사용할 수 없는 단어입니다."),
        /**
         * Loge 06 login error
         *
         * @author [오지훈]
         */
        LOGE06("새 비밀번호를 입력해주세요."),
        /**
         * Loge 07 login error
         *
         * @author [오지훈]
         */
        LOGE07("비밀번호가 틀렸습니다.");

        /**
         * 에러 메시지
         *
         * @author [이소정]
         */
        private final String value;

        /**
         * 생성자
         *
         * @param value - 에러 메시지
         * @author [이소정]
         * @CreatedOn 2020. 6. 24. 오후 5:25:57
         * @Description
         */
        LoginError(final String value) {
            this.value = value;
        }

        /**
         * 에러 메시지 조회
         *
         * @return value - 에러 메시지
         * @author [이소정]
         * @CreatedOn 2020. 6. 24. 오후 5:25:57
         * @Description
         */
        public String getMessage() {
            return value;
        }

    }

    /**
     * The enum Manage error.
     *
     * @author [이소정]
     */
    public enum UserError {

        /**
         * User 01 user error
         *
         * @author [오지훈]
         */
        USER01("해당 회원의 상세정보가 존재하지 않습니다.");

        /**
         * 에러 메시지
         *
         * @author [이소정]
         */
        private final String value;

        /**
         * 생성자
         *
         * @param value the value
         * @author [이소정]
         * @CreatedOn 2020. 6. 24. 오후 5:25:57
         * @Description
         */
        UserError(final String value) {
            this.value = value;
        }

        /**
         * 에러 메시지 조회
         *
         * @return the message
         * @author [이소정]
         * @CreatedOn 2020. 6. 24. 오후 5:25:57
         * @Description
         */
        public String getMessage() {
            return value;
        }

    }

    /**
     * The enum Data error.
     */
    public enum DataError {

        /**
         * Not found data error
         *
         * @author [오지훈]
         */
        NOT_FOUND("데이터가 존재하지 않습니다.");

        /**
         * 메시지
         *
         * @author [오지훈]
         */
        private final String value;

        /**
         * 생성자
         *
         * @param value the value
         * @author [오지훈]
         * @CreatedOn 2020. 6. 24. 오후 5:25:58
         * @Description
         */
        DataError(final String value) {
            this.value = value;
        }

        /**
         * 메시지 조회
         *
         * @return the message
         * @author [오지훈]
         * @CreatedOn 2020. 6. 24. 오후 5:25:58
         * @Description
         */
        public String getMessage() {
            return value;
        }

    }

}
