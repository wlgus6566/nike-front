package com.nike.dnp.dto.auth;


import com.nike.dnp.entity.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * AuthUserDTO
 *
 * @author [윤태호]
 * @CreatedOn 2020. 6. 24. 오후 6:07:49
 * @Description
 * @history [윤태호] [2020.05.22] [최초 작성]
 * @since 2020.05.22
 */
@Setter
@Getter
@RequiredArgsConstructor
public class AuthUserDTO implements UserDetails, Serializable {

	/**
	 * The constant serialVersionUID
	 *
	 * @author [윤태호]
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The User seq
	 *
	 * @author [윤태호]
	 */
	private Long userSeq;

	/**
	 * The User id
	 *
	 * @author [윤태호]
	 */
	private String userId;

	/**
	 * The Nickname
	 *
	 * @author [윤태호]
	 */
	private String nickname;

	/**
	 * The Password
	 *
	 * @author [윤태호]
	 */
	private String password;

	/**
	 * The Role
	 *
	 * @author [오지훈]
	 */
	private String role;

	/**
	 * The Auth seq
	 *
	 * @author [오지훈]
	 */
	private Long authSeq;

	/**
	 * Instantiates a new Auth user dto.
	 *
	 * @param user the user
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:07:49
	 * @Description
	 */
	public AuthUserDTO(final User user) {
		this.userSeq = user.getUserSeq();
		this.userId = user.getUserId();
		this.nickname = user.getNickname();
		this.password = user.getPassword();
		this.role = user.getUserAuth().getAuth().getRoleType();
		this.authSeq = user.getUserAuth().getAuth().getAuthSeq();
	}

	/**
	 * Gets password.
	 *
	 * @return the password
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:07:49
	 * @Description
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * Gets username.
	 *
	 * @return the username
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:07:49
	 * @Description
	 */
	@Override
	public String getUsername() {
		return userId;
	}

	/**
	 * Gets user seq.
	 *
	 * @return the user seq
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:07:49
	 * @Description
	 */
	public long getUserSeq() {return userSeq;}


	/**
	 * Gets authorities.
	 *
	 * @return the authorities
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:07:49
	 * @Description
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 유저 권한 정보 조회
		return Collections.singleton(new SimpleGrantedAuthority(role));
	}

	/**
	 * Is account non expired boolean.
	 *
	 * @return the boolean
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:07:49
	 * @Description
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Is account non locked boolean.
	 *
	 * @return the boolean
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:07:49
	 * @Description
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Is credentials non expired boolean.
	 *
	 * @return the boolean
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:07:49
	 * @Description
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Is enabled boolean.
	 *
	 * @return the boolean
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:07:49
	 * @Description
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Equals boolean.
	 *
	 * @param object the object
	 * @return the boolean
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:07:49
	 * @Description
	 */
	@Override
	public boolean equals(final Object object) {
		if(this == object) {
			return true;
		}

		if(!(object instanceof AuthUserDTO)) {
			return false;
		}

		final AuthUserDTO that = (AuthUserDTO) object;
		return Objects.equals(userId, that.userId);
	}

	/**
	 * Hash code int.
	 *
	 * @return the int
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:07:49
	 * @Description
	 */
	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	/**
	 * To string string.
	 *
	 * @return the string
	 * @author [오지훈]
	 * @CreatedOn 2020. 6. 24. 오후 6:07:49
	 * @Description
	 */
	@Override
	public String toString() {
		return "AuthUserDTO{"
				+ "userSeq=" + userSeq
				+ ", nickname='" + nickname + '\''
				+ ", userId='" + userId + '\'' + '}';
	}
}
