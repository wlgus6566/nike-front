package com.nike.dnp.controller.join;

import com.nike.dnp.common.variable.FailCode;
import com.nike.dnp.dto.join.JoinDTO;
import com.nike.dnp.exception.CodeMessageHandleException;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.service.RedisService;
import com.nike.dnp.service.ResponseService;
import com.nike.dnp.util.MessageUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 게시물 점유 관련
 *
 * @author [윤태호]
 * @since 2020. 8. 26. 오후 3:16:18
 */
@Slf4j
@RestController
@Api(description = "게시물 점유 관련", tags = "JOIN")
@RequestMapping(value = "/api/join", name = "게시물 점유 관련")
@RequiredArgsConstructor
public class JoinController {

    /**
     * The Response service
     *
     * @author [윤태호]
     */
    private final ResponseService responseService;
    /**
     * The Redis service
     *
     * @author [윤태호]
     */
    private final RedisService redisService;

    @Value("${nike.content.sessionTime:}")
    final int contentSessionTime;

    /**
     * 점유 시간 생성 및 갱신
     *
     * @param request the request
     * @param joinDTO the join dto
     * @return the single result
     * @author [윤태호]
     * @implNote 점유 시간 생성 및 갱신
     * @since 2020. 8. 26. 오후 3:16:18
     */
    @GetMapping(value = "/init", name = "점유시간 생성/갱신")
    public CommonResult joinTimeInit(HttpServletRequest request, JoinDTO joinDTO) {
        String token = request.getHeader("Authorization");
        if(!ObjectUtils.isEmpty(token)){
            final StringBuilder redisKey = new StringBuilder("content:");
            redisKey.append(joinDTO.getMenuName());
            redisKey.append(":");
            redisKey.append(joinDTO.getSeq());
            String redisToken = StringUtils.defaultIfBlank((String)redisService.get(redisKey.toString()),null) ;
            if(ObjectUtils.isEmpty(redisToken)){
                redisService.set(redisKey.toString(), token, contentSessionTime);
            }else{
                if(!redisToken.equals(token)){
                    throw new CodeMessageHandleException(FailCode.ConfigureError.DUPLICATE_CONTENT.name(),
                                                         MessageUtil.getMessage(FailCode.ConfigureError.DUPLICATE_CONTENT.name()));
                }else{
                    redisService.set(redisKey.toString(), token, contentSessionTime);
                }
            }
        }
        return responseService.getSuccessResult();
    }

    /**
     * 점유 시간 삭제
     *
     * @param request the request
     * @param joinDTO the join dto
     * @return the single result
     * @author [윤태호]
     * @implNote
     * @since 2020. 8. 26. 오후 4:00:40
     */
    @DeleteMapping(value = "/delete", name = "점유시간 삭제")
    public CommonResult joinRedisDelete(HttpServletRequest request, JoinDTO joinDTO) {
        String token = request.getHeader("Authorization");
        if(!ObjectUtils.isEmpty(token)){
            final StringBuilder redisKey = new StringBuilder("content:");
            redisKey.append(joinDTO.getMenuName());
            redisKey.append(":");
            redisKey.append(joinDTO.getSeq());
            String redisToken = StringUtils.defaultIfBlank((String) redisService.get(redisKey.toString()), null);
            if(!ObjectUtils.isEmpty(redisToken)){
                if(redisToken.equals(token)){
                    redisService.delete(redisKey.toString());
                }else{
                    throw new CodeMessageHandleException(FailCode.ConfigureError.NO_AUTH.name(), MessageUtil.getMessage(FailCode.ConfigureError.NO_AUTH.name()));
                }
            }
        }
        return responseService.getSuccessResult();
    }
}
