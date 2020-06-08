package com.nike.dnp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;

@Slf4j
@UtilityClass
public class JsonUtil{

	/**
	 * 오브젝트 json으로 변환
	 *
	 * @param object json
	 * @return	 String
	 */
	public String toJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try{
			return mapper.writeValueAsString(object);
		}catch(JsonProcessingException e){
			log.error("Failed to convert object to JSON string", e);
			return null;
		}
	}

	/**
	 * json > Class
	 *
	 * @param <T>   the type parameter
	 * @param json  json 스타일 문자열
	 * @param clazz 맵핑 클래스
	 * @return t
	 */
	public <T> T toObject(String json,
								 Class<T> clazz) {
		try{
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(json, clazz);
		}catch(IOException e){
			log.error("Failed to convert string '" + json + "' class '" + clazz.getName() + "'", e);
			return null;
		}
	}

	/**
	 * value  > json 형태로 writer
	 *
	 * @param writer the writer
	 * @param value  the value
	 * @throws IOException the io exception
	 */
	public void write(Writer writer,
							 Object value) throws IOException {
		new ObjectMapper().writeValue(writer, value);
	}

}