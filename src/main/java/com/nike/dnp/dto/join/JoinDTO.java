package com.nike.dnp.dto.join;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * The Class Join dto.
 *
 * @author [윤태호]
 * @since 2020. 8. 26. 오후 4:45:50
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class JoinDTO {


	/**
	 * The Menu name
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "menuName", value = "메뉴 명")
	private String menuName;

	/**
	 * The Seq
	 *
	 * @author [윤태호]
	 */
	@ApiModelProperty(name = "seq", value = "컨텐츠 키값")
	private Long seq;



}
