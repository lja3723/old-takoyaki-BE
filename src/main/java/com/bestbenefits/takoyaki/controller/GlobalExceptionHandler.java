package com.bestbenefits.takoyaki.controller;

import com.bestbenefits.takoyaki.config.apiresponse.ApiResponse;
import com.bestbenefits.takoyaki.config.apiresponse.ApiResponseCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    public ApiResponse<?> handleNoResourceFoundException(NullPointerException e){
        return ApiResponseCreator.fail(e.getMessage());
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ApiResponse<?> handleNoResourceFoundException(NoResourceFoundException e){
        return ApiResponseCreator.fail(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }
    @ExceptionHandler(WebClientException.class)
    public ApiResponse<?> handleWebClientException(WebClientException e){
        return ApiResponseCreator.fail(e.getMessage());
    }
    @ExceptionHandler(JsonProcessingException.class)
    public ApiResponse<?> handleJsonProcessingException(JsonProcessingException e){
        return ApiResponseCreator.fail(e.getMessage());
    }
}