package com.bestbenefits.takoyaki.DTO.client.request;

import com.bestbenefits.takoyaki.config.annotation.Nickname;
import lombok.Getter;

@Getter
public class UserDuplicateNicknameReqDTO {
    @Nickname
    private String nickname;
}
