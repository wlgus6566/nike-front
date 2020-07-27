package com.nike.dnp.common.variable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * The Class Success enum code.
 *
 * @author [이소정]
 * @CreatedOn 2020. 7. 1. 오후 5:01:10
 * @Description
 */
@Component
@AllArgsConstructor
public class SuccessEnumCode {

    /**
     * The enum Login success enum.
     */
    @RequiredArgsConstructor
    @Getter
    public enum LoginSuccess {
        SEND_EMAIL_CERT_CODE("해당 계정의 E-MAIL로 인증코드가 발송되었습니다. 이메일 확인 후 인증코드를 입력해 주세요."),
        SEND_EMAIL("E-MAIL이 발송되었습니다. E-MAIL을 확인주세요."),
        CHANGE_PASSWORD("설정한 비밀번호로 로그인 해 주세요."),
        TERMS_AGREEMENT("약관동의를 진행해 주세요.");

        /**
         * The Value
         * @author [이소정]
         */
        private final String message;
    }

    /**
     * The enum User success.
     */
    @RequiredArgsConstructor
    @Getter
    public enum UserSuccess {
        NOT_DUPLICATE("사용 가능한 ID입니다.");

        /**
         * 코드값
         *
         * @author [오지훈]
         */
        private final String message;
    }

    /**
     * The enum Order success enum.
     */
    @RequiredArgsConstructor
    @Getter
    public enum OrderSuccess {
        ADDED_WISHLIST("위시리스트에 추가 되었습니다. 위시리스트는 마이페이지에서 확인가능합니다."),
        ADDED_CART("CART에 담겼습니다."),
        SEND_ORDER_MAIL("주문메일이 발송 되었습니다.");

        /**
         * The Value
         * @author [이소정]
         */
        private final String message;
    }

}
