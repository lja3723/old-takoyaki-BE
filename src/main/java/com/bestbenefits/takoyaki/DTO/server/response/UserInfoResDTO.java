package com.bestbenefits.takoyaki.DTO.server.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfoResDTO {
    private KakaoAccount kakaoAccount;

    @Getter
    @RequiredArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public class KakaoAccount{
        private String email;
    }

    public String getEmail() {
        return Optional.ofNullable(kakaoAccount)
                .map(KakaoAccount::getEmail)
                .orElseThrow(() -> new NullPointerException("The email doesn't exist in your account."));
    }
}
