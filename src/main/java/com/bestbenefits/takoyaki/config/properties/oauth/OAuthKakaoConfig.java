package com.bestbenefits.takoyaki.config.properties.oauth;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.web.util.UriComponentsBuilder;

public final class OAuthKakaoConfig {
    public static final String URL1 = "https://kauth.kakao.com";
    public static final String URL2 = "https://kapi.kakao.com";
    public static final String AUTHORIZATION_CODE_URI = "/oauth/authorize";
    public static final String ACCESS_TOKEN_URI = "/oauth/token";
    public static final String REDIRECT_URI = "http://localhost:8080/oauth";
    //사용자 동의 항목
    public static final String AGREEMENT_URI = "/v2/user/scopes";
    public static final String USER_INFO_URI = "/v2/user/me";
    public static final String LOGOUT_URI = "/v1/user/logout";
    public static final String CLIENT_ID = "832c674221e0bd9d5f7a07f5c3ba5d85";
    public static final String AUTHORIZATION_CODE_URL = URL1 +
                    UriComponentsBuilder.newInstance()
                        .path(OAuthKakaoConfig.AUTHORIZATION_CODE_URI)
                        .queryParam("response_type", "code")
                        .queryParam("client_id", CLIENT_ID)
                        .queryParam("redirect_uri", OAuthKakaoConfig.REDIRECT_URI)
                        .queryParam("prompt", "select_account")
                        .build(true)
                        .toString();

    private OAuthKakaoConfig(){}
}
