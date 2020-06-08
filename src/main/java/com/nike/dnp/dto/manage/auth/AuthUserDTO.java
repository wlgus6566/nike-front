package com.nike.dnp.dto.manage.auth;


import com.nike.dnp.entity.manage.Manager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class AuthUserDTO implements UserDetails, Serializable {


	private long managerSeq;
	private String managerName;
	private String managerId;
	private String password;


	public AuthUserDTO(Manager manager) {
		this.managerSeq = manager.getManagerSeq();
		this.managerName = manager.getManagerName();
		this.managerId = manager.getManagerId();
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
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof AuthUserDTO))
			return false;
		AuthUserDTO that = (AuthUserDTO) o;
		return Objects.equals(managerName, that.managerName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(managerName);
	}

	@Override
	public String toString() {
		return "AuthUserDTO{" + "managerSeq=" + managerSeq + ", managerName='" + managerName + '\'' + ", managerId='" + managerId + '\'' + ", password='" + password + '\'' + '}';
	}
}
