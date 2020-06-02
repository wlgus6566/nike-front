package com.nike.dnp.advice;

import com.nike.dnp.exception.ManagerNotFoundException;
import com.nike.dnp.model.response.CommonResult;
import com.nike.dnp.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
//        return responseService.getFailResult();
//    }

    @ExceptionHandler(ManagerNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult managerNotFoundException(HttpServletRequest request, ManagerNotFoundException e) {
        return responseService.getFailResult();
    }

}
