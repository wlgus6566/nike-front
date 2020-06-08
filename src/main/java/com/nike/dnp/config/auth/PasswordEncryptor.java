package com.nike.dnp.config.auth;

public interface PasswordEncryptor {
	/**
	 * Encrypt a raw password
	 */
	String encrypt(String rawPassword);
}
