package com.nike.dnp.controller.order;


import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Order Controller
 *
 * @author [윤태호]
 * @CreatedOn 2020. 6. 26. 오후 3:27:34
 * @Description
 */
@Slf4j
@RestController
@Api(description = "주문", tags = "21_ORDER")
@RequestMapping(value = "/api/order/", name = "상품관리")
@AllArgsConstructor
public class OrderController {


}
