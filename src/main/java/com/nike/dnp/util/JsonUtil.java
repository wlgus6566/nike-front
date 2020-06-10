package com.nike.dnp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;

/**
 * JsonUtil
 *
 * @since 2020.05.21
 * @author [윤태호]
 * @Description JsonUtil 작성
 * @history [윤태호] [2020.05.21] [최초 작성]
 *
 */

@Slf4j
@UtilityClass
public class JsonUtil {

	/**
	 * value  > json 형태로 writer
	 *
	 * @param writer the writer
	 * @param value  the value
	 * @throws IOException the io exception
	 */
	public static void write(final Writer writer, final Object value) throws IOException {
		new ObjectMapper().writeValue(writer, value);
	}

}