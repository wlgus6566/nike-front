package com.nike.dnp.controller.join;

import com.nike.dnp.dto.auth.AuthUserDTO;
import com.nike.dnp.model.response.SingleResult;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.SecurityUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO[ojh] 2020/08/25 : 태호차장님 작업 요청드립니다.

@Slf4j
@RestController
@Api(description = "게시물 점유 관련", tags = "JOIN")
@RequestMapping(value = "/api/join", name = "게시물 점유 관련")
@RequiredArgsConstructor
public class JoinController {

    private final ResponseService responseService;
    private final RedisService redisService;


    @GetMapping(value = "/init", name = "점유시간 생성/갱신")
    public SingleResult<Object> joinTimeInit() {

        final AuthUserDTO authUserDTO = SecurityUtil.currentUser();



        return responseService.getSingleResult(null);
    }

}
