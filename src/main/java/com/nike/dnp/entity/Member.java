package com.nike.dnp.entity;

import com.nike.dnp.util.DateUtil;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity(name = "TB_MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;

    private String id;
    private String password;
    private String name;
    private String role;
    private String loginTime = DateUtil.getToday("yyyy.MM.dd HH:mm:ss");

    @Builder
    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
