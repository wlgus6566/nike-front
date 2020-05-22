package com.nike.dnp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Date Util
 *
 * @since 2020.05.21
 * @author [오지훈]
 * @Description DateUtil 작성
 * @history [오지훈] [2020.05.21] [최초 작성]
 * 
 */

public class DateUtil {

	/**
	 * 패턴에 따른 오늘 날짜 얻기
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getToday(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}

	/**
	 * 패턴에 따른 지정날짜 변환
	 * 
	 * @param paramDate
	 * @param format
	 * @return
	 */
	public static String getDate(String paramDate, String format) {
		String[] arrayDate = paramDate.substring(0, 10).split("-");
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, Integer.parseInt(arrayDate[0]));
		calendar.set(Calendar.MONTH, Integer.parseInt(arrayDate[1]) - 1);
		calendar.set(Calendar.DATE, Integer.parseInt(arrayDate[2]));

		Date date = new Date(calendar.getTimeInMillis());

		return ObjectUtil.isNull(format) ? new SimpleDateFormat("yyyyMMdd", Locale.KOREAN).format(date)
				: new SimpleDateFormat(format, Locale.KOREAN).format(date);
	}

	/**
	 * 날짜 패턴 변환
	 * 
	 * @param date
	 * @param orgPattern
	 * @param pattern
	 * @return
	 */
	public static String convertDateFormat(String date, String orgPattern, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(orgPattern);
		Date _date = sdf.parse(date);
		SimpleDateFormat sdf2 = new SimpleDateFormat(pattern);
		return sdf2.format(_date);
	}

	/**
	 * 해당 날짜 요일 구하기
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static int getDateDayInt(String date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(date));

			return cal.get(Calendar.DAY_OF_WEEK);
		} catch (Exception e) {
			e.printStackTrace();
			return 8;
		}
	}

	/**
	 * 해당 날짜 요일 구하기
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateDay(String date, String pattern) {
		String day = "";

		try {
			int dayNum = getDateDayInt(date, pattern);
			switch (dayNum) {
			case 1:
				day = "일";
				break;
			case 2:
				day = "월";
				break;
			case 3:
				day = "화";
				break;
			case 4:
				day = "수";
				break;
			case 5:
				day = "목";
				break;
			case 6:
				day = "금";
				break;
			case 7:
				day = "토";
				break;
			}
			return day;

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 시작일로부터 현재까지 주차 반환
	 * 
	 * @param start_date
	 * @param pattern
	 * @return
	 */
	public static String getWeeks(String start_date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date today = sdf.parse(DateUtil.getToday(pattern));
			Date _start_date = sdf.parse(start_date);

			long date_diff = (today.getTime() - _start_date.getTime()) / (24 * 60 * 60 * 1000);
			date_diff = (date_diff / 7) + 1;

			return String.valueOf(date_diff);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 현재 시간이 특정 시간대에 속하는지 검사
	 * 
	 * @param startDate 현재 시간과 비교할 시작 날짜
	 * @param endDate   현재 시간과 비교할 종료 날짜
	 * @param pattern
	 * @return
	 */
	public static boolean validateSpecificTimeZone(String today, String startDate, String endDate, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date _today = sdf.parse(today);
			Date start_date = sdf.parse(startDate);
			Date end_date = sdf.parse(endDate);

            return _today.compareTo(start_date) >= 0 && _today.compareTo(end_date) < 0;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 날짜 비교
	 * 
	 * @param pattern
	 * @param date1
	 * @param date2
	 * @throws ParseException
	 * @return
	 */
	public static int compareDate(String pattern, String date1, String date2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date _date1 = sdf.parse(date1);
		Date _date2 = sdf.parse(date2);
		return _date1.compareTo(_date2);
	}

	/**
	 * 오늘 날짜 기준으로 원하는 날짜 구하기
	 * 
	 * @param field   Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND
	 * @param amount  더하거나 뺄 수
	 * @param pattern 결과 날짜 패턴
	 * @return
	 */
	public static String getDateBasedOnToday(int field, int amount, String pattern) {
		Calendar calendar = new GregorianCalendar();
		calendar.add(field, amount);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 특정 날짜 기준으로 원하는 날짜 구하기
	 * 
	 * @param field        Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND
	 * @param amount       더하거나 뺄 수
	 * @param specificDate 기준이 되는 날짜
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static String getDateBaseOnSpecificDate(int field, int amount, String specificDate, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(sdf.parse(specificDate));
		calendar.add(field, amount);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 요일을 이용해 이번주 특정 날짜 구하기
	 * 
	 * @param value   Calendar.MONDAY ... Calendar.SUNDAY
	 * @param pattern
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getDateThisWeek(int value, String pattern) {
		Calendar calendar = new GregorianCalendar();
		if (calendar.get(calendar.DAY_OF_WEEK) == 1) {
			calendar.add(Calendar.DATE, -1);
		}

		if (Calendar.SUNDAY == value) {
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			calendar.add(Calendar.DATE, 1);

		} else {
			calendar.set(Calendar.DAY_OF_WEEK, value);
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(calendar.getTime());
	}

	/**
	 * 생년월일을 이용해 만 나이 계산
	 * <p>
	 * ex) {@code
	 * 	int age = DateUtil.getAmericanAge(member.getBirth());
	 * }
	 * 
	 * @param birth - 생년월일 (20200116)
	 * @return int - 만나이
	 */
	public static int getAmericanAge(String birth) {
		Calendar current = Calendar.getInstance();
		int currentYear = current.get(Calendar.YEAR);
		int currentMonth = current.get(Calendar.MONTH) + 1;
		int currentDay = current.get(Calendar.DAY_OF_MONTH);

		int birthYear = Integer.parseInt(birth.substring(0, 4));
		int birthMonth = Integer.parseInt(birth.substring(4, 6));
		int birthDay = Integer.parseInt(birth.substring(6, 8));

		int age = currentYear - birthYear;
		// 생일 안 지난 경우 -1
		if (birthMonth * 100 + birthDay > currentMonth * 100 + currentDay) {
			age--;
		}

		return age;
	}

	/**
	 * 해당일이 3개월전 인지 여부 확인
	 * 
	 * @return boolean - 3개월이 지났을 경우 {@code true}
	 */
	public static boolean isThreeMoth(Date targetDate) {
		if (ObjectUtil.isNull(targetDate)) {
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, -3);

		String targetDateStr = sdf.format(targetDate);
		String threeMonthDateStr = sdf.format(cal.getTime());

		return Integer.parseInt(targetDateStr) < Integer.parseInt(threeMonthDateStr);
	}
	
	/**
	 * 해당월이 몇 분기인지 구하기
	 * 
	 * @param month - 해당월
	 * @return int - 분기
	 */
	public static int getQuarter(int month) {
	  return (int)Math.ceil((double)month / 3);
	}
}