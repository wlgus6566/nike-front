package com.nike.dnp.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie Util
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description CookieUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */
@Component
public class CookieUtil {

	/**
	 * 쿠키 값 존재하는지 검사
	 * 
	 * @param request
	 * @param name
	 * @param value
	 * @return
	 */
	public boolean existCookieValue(HttpServletRequest request, String name, String value) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0) {
			return false;
		}

		boolean match = false;
		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName()) && value.equals(cookie.getValue())) {
				match = true;
			}
		}

		return match;
	}

	/**
	 * 쿠키 값 추가
	 * 
	 * @param name
	 * @param value
	 * @param path
	 * @param time
	 */
	public void addCookie(String name, String value, String path, int time) {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(path);
		cookie.setMaxAge(time);
		response.addCookie(cookie);
	}

	/**
	 * 쿠키 값 조회
	 * 
	 * @param name - 쿠키명
	 * @return Object - 쿠키가 존재할 경우 쿠키 데이터, 존재하지 않을 경우 {@code null} 리턴
	 */
	public Object getCookie(String name) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Cookie[] cookies = request.getCookies();
		Object returnData = null;

		if (!ObjectUtil.isNull(cookies)) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					returnData = cookie.getValue();
					break;
				}
			}
		}

		return returnData;
	}

	/**
	 * 쿠키 값 삭제
	 * 
	 * @param name - 쿠키명
	 * @return Object - 쿠키가 존재할 경우 쿠키 삭제
	 */
	public void removeCookie(String name) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		Cookie[] cookies = request.getCookies();

		if (!ObjectUtil.isNull(cookies)) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					break;
				}
			}
		}
	}

	/**
	 * 쿠키 타임아웃 값 계산
	 *
	 * @param autoLoginYn - 자동 로그인 여부
	 * @return long - 쿠키 타임아웃 값
	 */
	public int getCookieTimeout(String autoLoginYn) {
		if ("Y".equals(autoLoginYn)) {
			// 자동로그인 on인 경우 세션 기본 3개월
			return 60 * 60 * 24 * 30 * 3;
		} else {
			// 자동로그인 off인 경우 세션 로그인 기본 30분
			return 60 * 30;
		}
	}

}
