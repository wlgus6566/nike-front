package com.nike.dnp.dto.example.auth;


import com.nike.dnp.entity.example.Manager;
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
 * @since 2020.05.22
 * @author [윤태호]
 * @Description
 * @history [윤태호] [2020.05.22] [최초 작성]
 *
 */
@Setter
@Getter
@RequiredArgsConstructor
public class AuthUserDTO implements UserDetails, Serializable {

	/**
	 * @author [윤태호]
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @author [윤태호]
	 */
	private long userSeq;

	/**
	 * @author [윤태호]
	 */
	private String managerName;

	/**
	 * @author [윤태호]
	 */
	private String managerId;

	/**
	 * @author [윤태호]
	 */
	private String password;

	/**
	 * Instantiates a new Auth user dto.
	 *
	 * @param manager the manager
	 */
	public AuthUserDTO(final Manager manager) {
		this.userSeq = manager.getManagerSeq();
		this.managerName = manager.getManagerId();
		this.password = manager.getPassword();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return managerName;
	}

	public long getUserSeq() {return userSeq;}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 유저 권한 정보 조회
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(final Object object) {
		if(this == object) {
			return true;
		}

		if(!(object instanceof AuthUserDTO)) {
			return false;
		}

		final AuthUserDTO that = (AuthUserDTO) object;
		return Objects.equals(managerName, that.managerName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(managerName);
	}

	@Override
	public String toString() {
		return "AuthUserDTO{" + "managerSeq=" + userSeq + ", managerName='" + managerName + '\'' + ", managerId='" + managerId + '\'' + ", password='" + password + '\'' + '}';
	}
}
