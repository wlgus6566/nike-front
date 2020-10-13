package com.nike.dnp.dto.email;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Send dto.
 *
 * @author [오지훈]
 * @since 2020. 7. 3. 오전 11:22:37
 * @implNote
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class SendDTO {

    /**
     * The Nickname
     * 계정명
     *
     * @author [오지훈]
     */
    private String nickname = "";

    /**
     * The Email
     * 수신 이메일
     *
     * @author [오지훈]
     */
    private String email = "";

    /**
     * The Emails
     *
     * @author [오지훈]
     */
    private String[] emails;

    /**
     * The Cert code
     * 인증코드
     *
     * @author [오지훈]
     */
    private String certCode = "";

    /**
     * The Contents name
     * 컨텐츠 명
     *
     * @author [오지훈]
     */
    private String contentsName = "";

    /**
     * The Contents img.
     * 컨텐츠 이미지
     */
    private String contentsImg = "";

    /**
     * The Agency name
     * 에이전시 명
     *
     * @author [오지훈]
     */
    private String agencyName = "";

    /**
     * The Order comment
     * 주문 코멘트
     *
     * @author [오지훈]
     */
    private String orderComment = "";

    /**
     * The Order area
     * 주문상품 영역
     *
     * @author [오지훈]
     */
    private String orderArea = "";

    /**
     * The Order dt
     * 주문일
     *
     * @author [오지훈]
     */
    private String orderDt = "";

    /**
     * The Processing dt
     *
     * @author [오지훈]
     */
    private String processingDt = "";

    /**
     * The Login url
     * 로그인 링크
     *
     * @author [오지훈]
     */
    private String loginUrl;

    /**
     * The Password url
     * 패스워드 설정 링크
     *
     * @author [오지훈]
     */
    private String pwdUrl;

    /**
     * The Contents url
     * 컨텐츠 링크
     *
     * @author [오지훈]
     */
    private String contentsUrl;

    /**
     * The Product list
     * 상품 목록
     *
     * @author [오지훈]
     */
    private List<OrderProductDTO> productList = new ArrayList<>();

}
