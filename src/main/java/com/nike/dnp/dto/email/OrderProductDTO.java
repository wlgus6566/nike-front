package com.nike.dnp.dto.email;

import lombok.*;

/**
 * The Class Order product dto.
 *
 * @author [오지훈]
 * @since 2020. 7. 3. 오후 5:15:49
 * @implNote
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

}
