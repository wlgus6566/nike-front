package com.nike.dnp.config.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptorDelegator implements PasswordEncryptor {

	private final PasswordEncoder passwordEncoder;

	public PasswordEncryptorDelegator(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String encrypt(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}
}