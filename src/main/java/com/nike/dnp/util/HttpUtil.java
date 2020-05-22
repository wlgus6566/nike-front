package com.nike.dnp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpUrl Util
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description BeanUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

public class HttpUtil {

	/**
	 * Https Url Connection By POST
	 * 
	 * @param targetUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> sendHttpPost(String targetUrl, HashMap<String, Object> params) {
		HashMap<String, Object> responseData = new HashMap<>();
		int responseCode = 500;
		String responseMsg = "";
		String responseResult = "";

		try {
			URL url = new URL(targetUrl);
			String parameters = StringUtil.nvl(params.get("parameter"));
			String cookie = StringUtil.nvl(params.get("responseCookie"));

			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("POST"); // HTTP POST 메소드 설정
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			con.setRequestProperty("Cookie", cookie);
			con.setDoOutput(true); // POST 파라미터 전달을 위한 설정

			// Send post request
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(parameters);
			wr.flush();
			wr.close();

			responseCode = con.getResponseCode();
			responseMsg = con.getResponseMessage();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			con.disconnect();

			responseResult = response.toString();
			responseData = (HashMap<String, Object>) new ObjectMapper().readValue(responseResult, Map.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("responseCode", responseCode);
		map.put("responseMsg", responseMsg);
		map.put("responseResult", responseResult);
		map.put("responseData", responseData);

		return map;
	}

	/**
	 * Http Url Connection By GET
	 * 
	 * @param targetUrl
	 * @return
	 * @throws IOException
	 */
	public static HashMap<String, Object> sendHttpGet(String targetUrl) {
		System.out.println("targetUrl >>>>  "+targetUrl);
		HashMap<String, Object> map = new HashMap<String, Object>();
		int responseCode = 500; // 기본값 에러
		String responseMsg = "";
		String responseCookie = "";
		String responseResult = "";

		try {
			URL url = new URL(targetUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET"); // optional default is GET
			con.setRequestProperty("User-Agent", "Mozilla/5.0"); // add request header

			responseCode = con.getResponseCode();
			responseMsg = con.getResponseMessage();
			responseCookie = con.getHeaderField("Set-Cookie");
			responseResult = "";

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				if (inputLine.contains("_csrf")) {
					map.put("csrf", inputLine.substring(inputLine.indexOf("value=\"") + 7, inputLine.lastIndexOf("\"")));
					break;
				}
			}

			responseResult = response.toString();
			in.close();
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("responseCode", responseCode);
		map.put("responseMsg", responseMsg);
		map.put("responseCookie", responseCookie);
		map.put("responseResult", responseResult);

		return map;
	}

	public static CloseableHttpClient getHttpClient(int timeout) {
		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(timeout * 1000)
				.setSocketTimeout(timeout * 1000)
				.setConnectTimeout(timeout * 1000)
				.build();
		HttpClientBuilder httpClientBuilder = HttpClients.custom().setDefaultRequestConfig(config);
		return httpClientBuilder.build();
	}
}
