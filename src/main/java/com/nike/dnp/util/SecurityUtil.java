package com.nike.dnp.util;

import com.nike.dnp.dto.auth.AuthUserDTO;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The Class Security util.
 *
 * @author [윤태호]
 * @implNote
 * @since 2020. 7. 2. 오전 11:55:24
 */
@UtilityClass
public class SecurityUtil {

	/**
	 * Gets authentication.
	 *
	 * @return the authentication
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 2. 오전 11:56:16
	 */
	private static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * Current user auth user dto.
	 *
	 * @return the auth user dto
	 * @author [윤태호]
	 * @implNote
	 * @since 2020. 7. 2. 오전 11:56:16
	 */
	public static AuthUserDTO currentUser() {
		return (AuthUserDTO) getAuthentication().getPrincipal();
	}

}
