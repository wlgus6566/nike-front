package com.nike.dnp.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * RandomUtil
 *
 * @author [오지훈]
 * @CreatedOn 2020. 6. 19. 오후 4:53:33
 * @Description RandomUtil 작성
 */
@Slf4j
@UtilityClass
public class RandomUtil {

	/**
	 * Random cert code 1 string.
	 *
	 * @param size the size
	 * @return the string
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 19. 오후 5:45:12
	 * @Description 인증코드 생성 방법
	 */
	public static String randomCertCode1(int size) {
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < size; i++) {
			int rIndex = rnd.nextInt(7);
			switch (rIndex) {
				case 0:
					// a-z
					temp.append((char) (rnd.nextInt(26) + 97));
					break;
				case 1:
					// A-Z
					temp.append((char) (rnd.nextInt(26) + 65));
					break;
				case 2:
					// 0-9
					temp.append((rnd.nextInt(10)));
					break;
				case 3:
					// 특수문자1
					temp.append((char) (rnd.nextInt(16) + 33));
					break;
				case 4:
					// 특수문자2
					temp.append((char) (rnd.nextInt(7) + 58));
					break;
				case 5:
					// 특수문자3
					temp.append((char) (rnd.nextInt(6) + 91));
					break;
				case 6:
					// 특수문자4
					temp.append((char) (rnd.nextInt(4) + 123));
					break;
			}
		}

		return temp.toString();
	}

	/**
	 * Random cert code 2 string.
	 *
	 * @param size the size
	 * @return the string
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 19. 오후 5:45:12
	 * @Description 인증코드 생성 방법
	 */
	public static String randomCertCode2(int size) {
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < size; i++) {
			temp.append((char) (rnd.nextInt(93) + 33));
		}

		return temp.toString();
	}

}