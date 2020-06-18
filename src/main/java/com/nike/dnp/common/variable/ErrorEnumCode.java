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
 * @Description 서비스 별 에러 EnumCode
 * @history [이소정] [2020.06.03] [최초 작성]
 * @since 2020.06.03
 */
@Component
public class ErrorEnumCode {

    /**
     * The enum Common error.
     * @author [이소정]
     */
    public enum CommonError {
        COME01("로그인이 필요합니다."),
        COME02("권한이 없습니다.");

        /**
         * 에러 메시지
         * @author [이소정]
         */
        private final String value;

        /**
         * 생성자
         * @param value - 에러 메시지
         * @author [이소정]
         */
        CommonError(final String value) {
            this.value = value;
        }

        /**
         * 에러 메시지 조회
         *
         * @return value - 에러 메시지
         * @author [이소정]
         */
        public String getMessage() {
            return value;
        }
    }


    /**
     * The enum Login error.
     * @author [이소정]
     */
    public enum LoginError {

        LOGE01("가입여부를 확인해주세요.\n등록된 정보가 없습니다."),
        LOGE02("이메일을 정확히 입력해주세요."),
        LOGE03("비밀번호를 입력해주세요."),
        LOGE04("이미 사용중인 이메일 입니다."),
        LOGE05("사용할 수 없는 단어입니다."),
        LOGE06("새 비밀번호를 입력해주세요."),
        LOGE07("비밀번호가 틀렸습니다.");

        /**
         * 에러 메시지
         * @author [이소정]
         */
        private final String value;

        /**
         * 생성자
         * @param value - 에러 메시지
         * @author [이소정]
         */
        LoginError(final String value) {
            this.value = value;
        }

        /**
         * 에러 메시지 조회
         *
         * @return value - 에러 메시지
         * @author [이소정]
         */
        public String getMessage() {
            return value;
        }

    }

    /**
     * The enum Manage error.
     * @author [이소정]
     */
    public enum UserError {

        USER01("해당 회원의 상세정보가 존재하지 않습니다.");

        /**
         * 에러 메시지
         * @author [이소정]
         */
        private final String value;

        /**
         * 생성자
         * @param value
         * @author [이소정]
         */
        UserError(final String value) {
            this.value = value;
        }

        /**
         * 에러 메시지 조회
         *
         * @return the message
         * @author [이소정]
         */
        public String getMessage() {
            return value;
        }

    }



}
