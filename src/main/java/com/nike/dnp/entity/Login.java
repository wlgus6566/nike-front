package com.nike.dnp.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
public class Login extends User {

    private long seq;
    private String id;
    private String name;
    private String role;
    private String loginTime;

    public Login(String id, String password, Collection<? extends GrantedAuthority> authorities) {
        super(id, password, authorities);
    }

    public static Login of (Member member, List<GrantedAuthority> grantedAuthorities) {
        Login login = new Login(member.getId(), member.getPassword(), grantedAuthorities);
        login.setId(member.getId());
        login.setName(member.getName());
        login.setRole(member.getRole());
        login.setLoginTime(member.getLoginTime());
        return login;
    }
}
