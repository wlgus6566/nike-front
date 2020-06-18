package com.nike.dnp.common.variable;

import org.springframework.stereotype.Component;

/**
 * EnumCode
 * <p>
 * 이 클래스는 각 서비스별 발생할 수 있는 코드 정의입니다.
 *
 * @author [이소정]
 * @Description 서비스 별 EnumCode
 * @history [이소정] [2020.06.11] [최초 작성]
 * @since 2020.06.11
 */
@Component
public class EnumCode {

    /**
     * 정렬 순서
     * @author [이소정]
     */
    public enum SearchEnumCode {
        LATEST //최신순
        , START_DATE //시작일순
    }
}
