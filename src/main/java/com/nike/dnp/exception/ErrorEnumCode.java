package com.nike.dnp.exception;

import org.springframework.stereotype.Component;

/**
 * ErrorEnumCode<br><br>
 *
 * 이 클래스는 각 서비스별 발생할 수 있는 에러 코드와 메세지 정의입니다.<br>
 * 에러코드 작성 규칙은 해당 <b>서비스명 앞에서부터 3자 + E + 숫자</b> 입니다.<br>
 * ex) Login 서비스의 에러 코드 : LOG + E + 01 => LOGE01
 *
 * @since 2020.06.03
 * @author [이소정]
 * @Description 서비스 별 에러 EnumCode
 * @history [이소정] [2020.06.03] [최초 작성]
 *
 */
@Component
public class ErrorEnumCode {

    /**
     * The enum Login error.
     */
    public enum loginError {

        LOGE01("가입여부를 확인해주세요.\n등록된 정보가 없습니다."),
        LOGE02("이메일을 정확히 입력해주세요."),
        LOGE03("비밀번호를 입력해주세요."),
        LOGE04("이미 사용중인 이메일 입니다."),
        LOGE05("사용할 수 없는 단어입니다."),
        LOGE06("새 비밀번호를 입력해주세요.");

        /**
         * 생성자
         * @param value - 에러 메시지
         */
        loginError(String value) {
            this.value = value;
        }

        /**
         * 에러 메시지
         */
        private final String value;

        /**
         * 에러 메시지 조회
         *
         * @return value - 에러 메시지
         */
        public String getMessage() {
            return value;
        }

    }

    /**
     * The enum Manage error.
     */
    public enum manageError {

        MANE01("해당 회원의 상세정보가 존재하지 않습니다.");

        manageError(String value) {
            this.value = value;
        }
        private final String value;
        public String getMessage() {
            return value;
        }

    }



}
