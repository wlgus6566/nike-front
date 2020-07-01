package com.nike.dnp.common.variable;

import org.springframework.stereotype.Component;


/**
 * The Class Success enum code.
 *  <p>
 *  이 클래스는 성공 코드와 메세지에 대한 정의입니다.<br>
 *
 *  공통으로 사용하는 (등록성공, 수정성공, 삭제성공)에 대한 메세지는
 *  commonSuccess 메소드에 정의되어 있습니다.
 *
 *  그 외의 서비스에 맞는 성공코드 작성 규칙은 해당 <b>서비스명 앞에서부터 3자 + S + 숫자</b> 입니다.<br>
 *  ex) Login 서비스의 에러 코드 : LOG + S + 01 => LOGS01
 *
 * @author [이소정]
 * @CreatedOn 2020. 6. 30. 오후 5:40:17
 * @Description
 */
@Component
public class SuccessEnumCode {

    public enum AuthSuccessEnum {

    }
}
