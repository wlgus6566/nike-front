package com.nike.dnp.config.auth;

/**
 * The interface Password encryptor.
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
