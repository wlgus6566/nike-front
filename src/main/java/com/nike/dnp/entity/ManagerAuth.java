package com.nike.dnp.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
@Table(name = "TB_MANAGER_AUTH")
public class ManagerAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long authSeq;

    private String authName;

}
