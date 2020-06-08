package com.nike.dnp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * JSON Util
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description JsonUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

@Slf4j
public class JsonUtil {

	/**
	 * json 객체로 변환
	 * 
	 * @description Class > JSONObject
	 * @param classO
	 * @param object
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject toJson(Class<?> classO, Object object) throws UnsupportedEncodingException {
		JSONObject jsonObject = new JSONObject();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		Class<?> classR = classO;

		while (classR != null && classR != Object.class) {
			for (Field field : classR.getDeclaredFields()) {
				if (!field.isSynthetic()) {
					char[] charArray = field.getName().substring(0, 1).toCharArray();
					String fieldName = "get" + Character.toUpperCase(charArray[0]) + field.getName().substring(1);
					Object fieldValue;

					try {
						fieldValue = classR.getDeclaredMethod(fieldName).invoke(object);
						hashMap.put(field.getName(), ObjectUtil.nvl(fieldValue));

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			classR = classR.getSuperclass();
		}

		Iterator<String> keyIterator = hashMap.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = String.valueOf(keyIterator.next());
			String value = String.valueOf(hashMap.get(key));
			jsonObject.put(key, value);
		}

		return jsonObject;
	}

	public static JSONObject getJsonStringFromMap( HashMap<String, Object> map )
	{
		JSONObject jsonObject = new JSONObject();
		for( Map.Entry<String, Object> entry : map.entrySet() ) {
			String key = entry.getKey();
			Object value = entry.getValue();
			jsonObject.put(key, value);
		}

		return jsonObject;
	}

	/**
	 * json 리스트로 변환
	 * 
	 * @description List > JSONArray 변환
	 * @param list
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JSONArray toJsonList(List list) {
		JSONArray jsonArray = new JSONArray();

		for (Object object : list) {
			try {
				JSONObject jsonObject = toJson(object.getClass(), object);
				jsonArray.add(jsonObject);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return jsonArray;
	}

	/**
	 * 오브젝트 json으로 변환
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
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
	 * @param json
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T toObject(String json,
								 Class<T> clazz) {
		try{
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(json, clazz);
		}catch(IOException e){
			log.error("Failed to convert string `" + json + "` class `" + clazz.getName() + "`", e);
			return null;
		}
	}

	/**
	 * value  > json 형태로 writer
	 * @param writer
	 * @param value
	 * @throws IOException
	 */
	public static void write(Writer writer,
							 Object value) throws IOException {
		new ObjectMapper().writeValue(writer, value);
	}
}
