package com.nike.dnp.dto.email;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class OrderProductDTO {

    private String productName = "";
    private String productDesc = "";
    private int amount;

}
