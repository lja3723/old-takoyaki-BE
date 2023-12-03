package com.bestbenefits.takoyaki.config.apiresponse;

import org.springframework.http.HttpStatus;

public class ApiResponseCreator {
    public static <D> ApiResponse<D> success(D data){
        return new ApiResponse<>(true, data, HttpStatus.OK.value());
    }
    public static <D> ApiResponse<D> success(D data, int status){
        return new ApiResponse<>(true, data, status);
    }
    public static ApiResponse<ApiMessage> fail(String message){
        return new ApiResponse<>(false, new ApiMessage(message), HttpStatus.BAD_REQUEST.value());
    }
    public static ApiResponse<ApiMessage> fail(String message, int status){
        return new ApiResponse<>(false, new ApiMessage(message), status);
    }
}
