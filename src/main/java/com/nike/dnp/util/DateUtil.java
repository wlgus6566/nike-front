package com.nike.dnp.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

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

@Slf4j
@UtilityClass
public class DateUtil {

	/**
	 * 패턴에 따른 오늘 날짜 얻기
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getToday(String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
		return simpleDateFormat.format(new Date());
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
	 * @param dateCharacter the date character
	 * @param orgPattern    the org pattern
	 * @param pattern       the pattern
	 * @return string string
	 * @throws ParseException the parse exception
	 */
	public static String convertDateFormat(String dateCharacter, String orgPattern, String pattern) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(orgPattern, Locale.KOREAN);
		Date date = simpleDateFormat.parse(dateCharacter);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern, Locale.KOREAN);
		return simpleDateFormat2.format(date);
	}

	/**
	 * 해당 날짜 요일 구하기
	 *
	 * @param dateCharacter the date character
	 * @param pattern       the pattern
	 * @return date day int
	 */
	public static int getDateDayInt(String dateCharacter, String pattern) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.KOREAN);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(simpleDateFormat.parse(dateCharacter));

			return calendar.get(Calendar.DAY_OF_WEEK);
		} catch (ParseException exception) {
			log.error("###Exception Error###", exception);
			return 8;
		}
	}

	/**
	 * 해당 날짜 요일 구하기
	 *
	 * @param dateCharacter the date character
	 * @param pattern       the pattern
	 * @return date day
	 */
	public static String getDateDay(String dateCharacter, String pattern) {
		String dayOfTheWeek;
		switch (getDateDayInt(dateCharacter, pattern)) {
			case 1:
				dayOfTheWeek = "일";
				break;
			case 2:
				dayOfTheWeek = "월";
				break;
			case 3:
				dayOfTheWeek = "화";
				break;
			case 4:
				dayOfTheWeek = "수";
				break;
			case 5:
				dayOfTheWeek = "목";
				break;
			case 6:
				dayOfTheWeek = "금";
				break;
			case 7:
				dayOfTheWeek = "토";
				break;
			default:
				dayOfTheWeek = "";
				break;
		}
		return dayOfTheWeek;
	}

	/**
	 * 시작일로부터 현재까지 주차 반환
	 *
	 * @param startDate the start date
	 * @param pattern   the pattern
	 * @return weeks
	 */
	public static String getWeeks(String startDate, String pattern) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.KOREAN);
			Date today = simpleDateFormat.parse(DateUtil.getToday(pattern));
			Date startDateDate = simpleDateFormat.parse(startDate);
			return String.valueOf(((today.getTime() - startDateDate.getTime()) / (24 * 60 * 60 * 1000) / 7) + 1);

		} catch (Exception exception) {
			log.error("###Exception Error###", exception);
			return null;
		}
	}

	/**
	 * 현재 시간이 특정 시간대에 속하는지 검사
	 *
	 * @param todayCharacter     the today character
	 * @param startDateCharacter the start date character
	 * @param endDateCharacter   the end date character
	 * @param pattern            the pattern
	 * @return boolean boolean
	 */
	public static boolean validateSpecificTimeZone(String todayCharacter, String startDateCharacter, String endDateCharacter, String pattern) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.KOREAN);
			Date today = simpleDateFormat.parse(todayCharacter);
			Date startDate = simpleDateFormat.parse(startDateCharacter);
			Date endDate = simpleDateFormat.parse(endDateCharacter);
            return today.compareTo(startDate) >= 0 && today.compareTo(endDate) < 0;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * 날짜 비교
	 *
	 * @param pattern        the pattern
	 * @param dateCharacter1 the date character 1
	 * @param dateCharacter2 the date character 2
	 * @return int
	 * @throws ParseException the parse exception
	 */
	public static int compareDate(String pattern, String dateCharacter1, String dateCharacter2) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.KOREAN);
		Date date1 = simpleDateFormat.parse(dateCharacter1);
		Date date2 = simpleDateFormat.parse(dateCharacter2);
		return date1.compareTo(date2);
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
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.KOREAN);
		return simpleDateFormat.format(calendar.getTime());
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
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.KOREAN);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(simpleDateFormat.parse(specificDate));
		calendar.add(field, amount);
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

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREAN);
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MONTH, -3);

		String targetDateStr = simpleDateFormat.format(targetDate);
		String threeMonthDateStr = simpleDateFormat.format(cal.getTime());

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