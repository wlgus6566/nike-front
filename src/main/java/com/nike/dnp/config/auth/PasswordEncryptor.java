package com.nike.dnp.config.auth;

/**
 * 암호화 모듈
 */
public interface PasswordEncryptor {
	/**
	 * Encrypt a raw password
	 *
	 * @param rawPassword the raw password
	 * @return the string
	 */
	String encrypt(String rawPassword);
}
