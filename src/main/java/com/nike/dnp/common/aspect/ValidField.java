package com.nike.dnp.common.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * The Interface Valid field.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 22. 오후 2:11:07
 * @Description 필수로 입력되어야 하는 필드를 체크한다.
 */
@Target(ElementType.METHOD)
public @interface ValidField {
}
