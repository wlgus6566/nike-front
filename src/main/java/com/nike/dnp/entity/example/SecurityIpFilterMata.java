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
 * @history [윤태호] [2020.06.11] [최초 작성]
 * @since 2020.06.11
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name="TB_SECURITY_IP_FILTER_MATA")
public class SecurityIpFilterMata extends BasicDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;

	/**
	 *
	 */
	@Column(name="IP")
	private String ip;

	/**
	 *
	 */
	@Column(name = "sort")
	private String sort;
}
