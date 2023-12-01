package com.bestbenefits.takoyaki.controller;

import com.bestbenefits.takoyaki.config.apiresponse.ApiMessage;
import com.bestbenefits.takoyaki.config.apiresponse.ApiResponse;
import com.bestbenefits.takoyaki.config.apiresponse.ApiResponseCreator;
import com.bestbenefits.takoyaki.config.properties.oauth.OAuthKakaoConfig;
import com.bestbenefits.takoyaki.service.UserService;
import com.bestbenefits.takoyaki.util.CustomJson;
import com.bestbenefits.takoyaki.util.webclient.oauth.OAuthWebClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.HashMap;
import java.util.Map;


@RestController
//@Validated
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final Map<String, OAuthWebClient> oAuthWebClient;


    @GetMapping("/oauth2/auth_code_url/{social}")
    public ApiResponse<?> getOauth2AuthCodeUrl(@PathVariable String social){
        //kakao, naver, google 중 하나인지 확인하는 로직 추가하기

        Map<String, String> map = new HashMap<>();
        map.put("auth_code_url", OAuthKakaoConfig.AUTHORIZATION_CODE_URL);

        return ApiResponseCreator.success(map, HttpStatus.OK.value());
    }


    @PostMapping("/oauth2/login/{social}")
    public ApiResponse<?> login(@RequestParam String code, @PathVariable String social) throws JsonProcessingException{
        //kakao, naver, google 중 하나인지 확인하는 로직 추가하기

        //세션 구현

        String response;

        OAuthWebClient oAuthSocialWebClient = oAuthWebClient.get("OAuthKakaoWebClient");

        //access token 요청
        response = oAuthSocialWebClient.requestAccessToken(code);

        //응답에서 access token 빼내기
        String accessToken = CustomJson.convertJsonString2JsonNode(response)
                            .get("access_token")
                            .asText();

        //access token으로 유저가 동의한 항목에 대한 정보 요청하기
        response = oAuthSocialWebClient.requestUserInfo(accessToken);

        //가져온 유저 정보(json문자열)를 json 객체로 만들기
        JsonNode userInfo = CustomJson.convertJsonString2JsonNode(response);

        //DB에 등록하기

        //성공했다고 알리기
        return ApiResponseCreator.success(new ApiMessage(""), HttpStatus.CREATED.value());
    }

}
