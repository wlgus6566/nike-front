package com.nike.dnp.dto.email;

import com.nike.dnp.dto.order.OrderProductFileSaveDTO;
import com.nike.dnp.entity.order.OrderProductFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * The Class Order product dto.
 *
 * @author [오지훈]
 * @implNote
 * @since 2020. 7. 3. 오후 5:15:49
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class OrderProductDTO {

    /**
     * The Product name
     *
     * @author [오지훈]
     */
    private String productName = "";

    /**
     * The Product desc
     *
     * @author [오지훈]
     */
    private String productDesc = "";

    /**
     * The Amount
     *
     * @author [오지훈]
     */
    private int amount;

    /**
     * 이미지 파일
     *
     * @author [윤태호]
     */
    private String imageFilePhysicalName;

    /**
     * The Product comment
     *
     * @author [이소정]
     */
    private String productComment;

    /**
     * The File list
     *
     * @author [이소정]
     */
    private List<OrderProductFile> productFileList;

}
