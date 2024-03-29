package com.nike.dnp.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

/**
 * RandomUtil
 *
 * @author [오지훈]
 * @since 2020. 6. 19. 오후 4:53:33
 * @implNote RandomUtil 작성
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
	 * @since 2020. 6. 19. 오후 5:45:12
	 * @implNote 인증코드 생성 방법
	 */
	public String randomCertCode2(final int size) {
		final StringBuilder temp = new StringBuilder();
		final SecureRandom rnd = new SecureRandom();
		for (int i = 0; i < size; i++) {
			switch (rnd.nextInt(7)) {
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
					temp.append(rnd.nextInt(10));
					break;
				case 3:
					// 특수문자1
					int srKey = (rnd.nextInt(16) + 33);
					if (srKey == 34 || srKey == 39) {
						temp.append("X");
						break;
					}
					temp.append((char) srKey);
					break;
				case 4:
					// 특수문자2
					int srKey1 = (rnd.nextInt(7) + 58);
					if (srKey1 == 60 || srKey1 == 62) {
						temp.append("Z");
						break;
					}
					temp.append((char) srKey1);
					break;
				case 5:
					// 특수문자3
					temp.append((char) (rnd.nextInt(6) + 91));
					break;
				case 6:
					// 특수문자4
					temp.append((char) (rnd.nextInt(4) + 123));
					break;
				default:
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
	 * @since 2020. 6. 19. 오후 5:45:12
	 * @implNote 인증코드 생성 방법
	 */
	/*public String randomCertCode(final int size) {
		final StringBuilder temp = new StringBuilder();
		final SecureRandom rnd = new SecureRandom();
		for (int i = 0; i < size; i++) {
			temp.append((char) (rnd.nextInt(93) + 33));
		}

		return temp.toString();
	}*/

}