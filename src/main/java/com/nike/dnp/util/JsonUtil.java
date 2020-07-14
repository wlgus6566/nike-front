package com.nike.dnp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nike.dnp.common.variable.ErrorEnumCode;
import com.nike.dnp.exception.CodeMessageHandleException;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;

/**
 * JsonUtil
 *
 * @author [윤태호]
 * @CreatedOn 2020. 6. 24. 오후 6:03:44
 * @Description JsonUtil 작성
 * @history [윤태호] [2020.05.21] [최초 작성]
 * @since 2020.05.21
 */
@Slf4j
@UtilityClass
@AllArgsConstructor
public class JsonUtil {

	/**
	 * Write.
	 *
	 * @param writer the writer
	 * @param value  the value
	 * @author [윤태호]
	 * @CreatedOn 2020. 6. 24. 오후 6:03:44
	 * @Description value > json 형태로 writer
	 */
	public void write(final Writer writer, final Object value) {
		try {
			new ObjectMapper().writeValue(writer, value);
		} catch (IOException exception) {
			throw new CodeMessageHandleException(
					ErrorEnumCode.ExceptionError.ERROR.toString()
					, exception.getMessage());
		}
	}

}