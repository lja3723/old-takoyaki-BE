package com.bestbenefits.takoyaki.util.webclient.oauth;

public interface OAuthWebClient {
    String requestAccessToken(String code);
    String requestUserInfo(String accessToken);
}