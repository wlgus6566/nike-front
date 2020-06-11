package com.nike.dnp.config.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * The type Password encryptor delegator.
 */
@Component
public class PasswordEncryptorDelegator implements PasswordEncryptor {

	/**
	 *
	 */
	private final transient PasswordEncoder passwordEncoder;

	/**
	 * Instantiates a new Password encryptor delegator.
	 *
	 * @param passwordEncoder the password encoder
	 */
	public PasswordEncryptorDelegator(final PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String encrypt(final String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}
}