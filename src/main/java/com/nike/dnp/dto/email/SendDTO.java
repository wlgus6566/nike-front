package com.nike.dnp.dto.email;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class SendDTO {

    private String nickname = "";
    private String email = "";
    private String certCode = "";
    private String contentsName = "";
    private String agencyName = "";
    private String orderComment = "";
    private String orderArea = "";
    private String orderDt = "";

    //TODO[ojh] 2020-07-02 : 변경예정
    private String loginUrl = "http://nikednp.co.kr/login";
    private String passwordUrl = "http://nikednp.co.kr/password";
    private String contentsUrl = "http://nikednp.co.kr/contents/1";

    private List<OrderProductDTO> productList = new ArrayList<>();
}
