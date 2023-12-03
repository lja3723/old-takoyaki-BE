package com.bestbenefits.takoyaki.util.webclient.oauth;

import com.bestbenefits.takoyaki.config.properties.oauth.OAuthKakaoConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

//카카오로 보낼 요청 종류에 따라 요청을 만들고 보내는 클래스
@Component
@PropertySource("classpath:application.yml")
public class OAuthKakaoWebClient implements OAuthWebClient{

    @Value("${oauth2.client_secret.kakao}")
    private String KAKAO_CLIENT_SECRET;

    public String requestTokens(String code) {
        WebClient webClient = WebClient.builder()
                .baseUrl(OAuthKakaoConfig.URL1)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
        String uri = UriComponentsBuilder.newInstance()
                .path(OAuthKakaoConfig.ACCESS_TOKEN_URI)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", OAuthKakaoConfig.CLIENT_ID)
                .queryParam("redirect_uri", OAuthKakaoConfig.REDIRECT_URI)
                .queryParam("code", code)
                .queryParam("client_secret", KAKAO_CLIENT_SECRET)
                .build(true)
                .toString();
        return webClient.post()
                        .uri(uri)
                        .retrieve()
                        //잘 모르니 쓰지 말자.
//                        .onStatus(HttpStatusCode::isError, clientResponse ->
//                        Mono.error(new OAuthWebClientException("failed")))
                        .bodyToMono(String.class)
                        .block();
    }

    public String requestAccessToken(String refreshToken){
        WebClient webClient = WebClient.builder()
                .baseUrl(OAuthKakaoConfig.URL1)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
        String uri = UriComponentsBuilder.newInstance()
                .path(OAuthKakaoConfig.ACCESS_TOKEN_URI)
                .queryParam("grant_type", "refresh_token")
                .queryParam("client_id", OAuthKakaoConfig.CLIENT_ID)
                .queryParam("refresh_token", refreshToken)
                .queryParam("client_secret", KAKAO_CLIENT_SECRET)
                .build(true)
                .toString();
        return webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String requestUserInfo(String accessToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl(OAuthKakaoConfig.URL2)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
        String uri = UriComponentsBuilder.newInstance()
                .path(OAuthKakaoConfig.USER_INFO_URI)
                .build(true)
                .toString();
        return webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}