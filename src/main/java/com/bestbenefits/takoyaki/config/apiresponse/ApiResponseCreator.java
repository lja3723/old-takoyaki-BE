package com.bestbenefits.takoyaki.config.apiresponse;

import org.springframework.http.HttpStatus;

public class ApiResponseCreator {
    public static <D> ApiResponse<D> success(D data){
        return new ApiResponse<>(true, data, HttpStatus.OK.value());
    }
    public static <D> ApiResponse<D> success(D data, int status){
        return new ApiResponse<>(true, data, status);
    }
    public static ApiResponse<String> fail(String message){
        return new ApiResponse<>(false, message, HttpStatus.BAD_REQUEST.value());
    }
    public static ApiResponse<String> fail(String message, int status){
        return new ApiResponse<>(false, message, status);
    }
}
