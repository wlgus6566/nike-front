package com.nike.dnp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;

@Slf4j
@UtilityClass
public class JsonUtil{

	/**
	 * value  > json 형태로 writer
	 *
	 * @param writer the writer
	 * @param value  the value
	 * @throws IOException the io exception
	 */
	public void write(final Writer writer, final Object value) throws IOException {
		new ObjectMapper().writeValue(writer, value);
	}

}