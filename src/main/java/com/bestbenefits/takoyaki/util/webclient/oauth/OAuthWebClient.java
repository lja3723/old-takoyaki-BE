package com.bestbenefits.takoyaki.util.webclient.oauth;

public interface OAuthWebClient {
    String requestTokens(String code);
    String requestAccessToken(String refreshToken);
    String requestUserInfo(String accessToken);
}