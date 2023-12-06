package com.bestbenefits.takoyaki.util.webclient.oauth;

import com.bestbenefits.takoyaki.DTO.server.response.TokensResDTO;
import com.bestbenefits.takoyaki.DTO.server.response.UserInfoResDTO;

public interface OAuthWebClient {
    TokensResDTO requestTokens(String code);
    String requestAccessToken(String refreshToken);
    UserInfoResDTO requestUserInfo(String accessToken);
}