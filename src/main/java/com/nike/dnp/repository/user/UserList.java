package com.nike.dnp.repository.user;

import com.nike.dnp.entity.user.UserAuth;
import org.springframework.stereotype.Component;

@Component
public interface UserList {

    String getUserId();

    String getNickname();

    UserAuth getUserAuth();

}
