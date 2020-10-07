package com.nike.dnp.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

/**
 * ConvertUtil
 *
 * @author [오지훈]
 * @since 2020. 6. 19. 오후 4:53:33
 * @implNote ConvertUtil 작성
 */
@Slf4j
@UtilityClass
public class ConvertUtil {

	/**
	 * Convert char to string string.
	 *
	 * @param value the value
	 * @return the string
	 * @author [오지훈]
	 * @implNote char[] > String
	 * @since 2020. 10. 7. 오전 10:45:42
	 */
	public String convertCharacterToString(final char[] value) {
		log.info("ConvertUtil.convertCharacterToString");
		return ObjectUtils.isEmpty(value) ? "" : new String(value);
	}

	/**
	 * Convert string to character char [ ].
	 *
	 * @param value the value
	 * @return the char [ ]
	 * @author [오지훈]
	 * @implNote String > char[]
	 * @since 2020. 10. 7. 오전 10:49:19
	 */
	public char[] convertStringToCharacter(final String value) {
		log.info("ConvertUtil.convertStringToCharacter");
		return ObjectUtils.isEmpty(value) ? null : value.toCharArray();
	}

	/**
	 * cleanValue
	 *
	 * @param value the value
	 * @return the char [ ]
	 * @author [오지훈]
	 * @implNote 문자 치환
	 * @since 2020. 10. 7. 오전 10:49:19
	 */
	public void cleanValue (char[] value) {
		if(!ObjectUtils.isEmpty(value)) Arrays.fill(value, '*');
	}

}