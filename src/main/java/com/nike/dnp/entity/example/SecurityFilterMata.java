package com.nike.dnp.entity.example;

import com.nike.dnp.dto.BasicDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


/**
 * SecurityFilterMata Entity
 *
 * @author [윤태호]
 * @Description SecurityFilterMata Entity 작성
 * @history [오지훈] [2020.06.11] [최초 작성]
 * @since 2020.06.11
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name="TB_SECURITY_FILTER_MATA")
public class SecurityFilterMata extends BasicDTO implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	@Column(name="ANT_PATTERN")
	private String antPattern;

	@Column(name = "EXPRESSION")
	private String expression;

	@Column(name = "HTTP_METHOD")
	private String httpMethod;

	@Column(name = "SORT")
	private Integer sort;




}
