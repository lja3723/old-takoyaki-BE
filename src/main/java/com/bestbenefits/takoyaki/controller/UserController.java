package com.bestbenefits.takoyaki.controller;

import com.bestbenefits.takoyaki.DTO.client.request.UserAdditionalInfoReqDTO;
import com.bestbenefits.takoyaki.DTO.client.request.UserNicknameUpdateReqDTO;
import com.bestbenefits.takoyaki.DTO.layer.request.OAuthSignUpReqDTO;
import com.bestbenefits.takoyaki.DTO.layer.response.OAuthAuthResDTO;
import com.bestbenefits.takoyaki.DTO.client.request.UserDuplicateNicknameReqDTO;
import com.bestbenefits.takoyaki.DTO.server.response.TokensResDTO;
import com.bestbenefits.takoyaki.DTO.server.response.UserInfoResDTO;
import com.bestbenefits.takoyaki.config.annotation.Session;
import com.bestbenefits.takoyaki.config.apiresponse.ApiMessage;
import com.bestbenefits.takoyaki.config.apiresponse.ApiResponse;
import com.bestbenefits.takoyaki.config.apiresponse.ApiResponseCreator;
import com.bestbenefits.takoyaki.config.properties.SessionConst;
import com.bestbenefits.takoyaki.config.properties.oauth.OAuthSocialType;
import com.bestbenefits.takoyaki.config.properties.oauth.OAuthURL;
import com.bestbenefits.takoyaki.service.UserService;
import com.bestbenefits.takoyaki.util.webclient.oauth.OAuthWebClient;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final Map<String, OAuthWebClient> oAuthWebClient;
    private final Map<String, OAuthURL> oAuthURL;

    @GetMapping("/login-check")
    public ApiResponse<?> checkLogin(HttpSession session){
        Map<String, Boolean> data = new HashMap<>();
        data.put("login", session.getAttribute(SessionConst.AUTHENTICATION) != null);
        return ApiResponseCreator.success(data);
    }

    @GetMapping("/oauth/login-url/{social}")
    public ApiResponse<?> getOAuthLoginUrl(@PathVariable String social){
        OAuthSocialType oAuthSocialType = OAuthSocialType.fromValue(social.toUpperCase());
        OAuthURL oAuthSocialURL = oAuthURL.get("OAuth" + oAuthSocialType.getPascalName() + "URL");

        Map<String, String> data = new HashMap<>();
        data.put("login_url", oAuthSocialURL.getLoginURL());

        return ApiResponseCreator.success(data);
    }

    @GetMapping("/duplicate-nickname")
    public ApiResponse<?> checkDuplicateNickname(@RequestBody @Valid UserDuplicateNicknameReqDTO userDuplicateNicknameReqDTO){

        Map<String, Boolean> data = new HashMap<>();
        data.put("duplicate-nickname", userService.checkDuplicateNickname(userDuplicateNicknameReqDTO));

        return ApiResponseCreator.success(data);
    }

    @PostMapping("/oauth/login/{social}")
    public ApiResponse<?> login(HttpSession session, @PathVariable String social, @RequestParam String code){
        //get social-type enum
        OAuthSocialType oAuthSocialType = OAuthSocialType.fromValue(social.toUpperCase());
        //소셜 플랫폼에 따라 OAuth 요청을 수행할 객체를 가져옴
        OAuthWebClient oAuthSocialWebClient = oAuthWebClient.get("OAuth"+ oAuthSocialType.getPascalName()+"WebClient");
        //request tokens to resource server by Authorization code
        TokensResDTO tokensResDTO = oAuthSocialWebClient.requestTokens(code);
        //request user's info to resource server by access token
        UserInfoResDTO userInfoResDTO = oAuthSocialWebClient.requestUserInfo(tokensResDTO.getAccessToken());
        //check whether this user exists in DataBase by using email & social type
        OAuthAuthResDTO oAuthAuthResDTO = userService.loginByOAuth(userInfoResDTO.getEmail(), oAuthSocialType.ordinal());

        if (oAuthAuthResDTO != null) { //등록된 유저고,
            if(!oAuthAuthResDTO.isInfoNeeded()) //추가 정보 있으면 로그인 완료
                session.setAttribute(SessionConst.AUTHENTICATION, true);
        }else //등록되지 않은 유저면 유저 등록
            oAuthAuthResDTO = userService.signUpByOAuth(OAuthSignUpReqDTO.builder()
                                          .userInfoResDTO(userInfoResDTO)
                                           .social(oAuthSocialType.ordinal())
                                            .build());

        session.setAttribute(SessionConst.ID, oAuthAuthResDTO.getId());

        Map<String, Boolean> data = new HashMap<>();
        data.put("is_info_needed", oAuthAuthResDTO.isInfoNeeded());

        return ApiResponseCreator.success(data, HttpStatus.CREATED.value());
    }

    @PostMapping("/oauth/login/additional-info")
    public ApiResponse<?> signup(HttpSession session,
                                 @Session(attribute = SessionConst.ID) Long id,
                                 @RequestBody @Valid UserAdditionalInfoReqDTO userAdditionalInfoReqDTO){
        userService.insertAdditionalInfo(id, userAdditionalInfoReqDTO);
        session.setAttribute(SessionConst.AUTHENTICATION, true);
        return ApiResponseCreator.success(new ApiMessage("추가 정보 입력이 완료되었습니다."), HttpStatus.CREATED.value());
    }

    @PatchMapping("/nickname")
    public ApiResponse<?> changeNickname(@Session(attribute = SessionConst.ID) Long id,
                                         @RequestBody @Valid UserNicknameUpdateReqDTO userNicknameUpdateReqDTO){
        userService.changeNickname(id, userNicknameUpdateReqDTO);
        return ApiResponseCreator.success(new ApiMessage("닉네임 변경이 완료되었습니다."));
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpSession session){
        session.removeAttribute(SessionConst.AUTHENTICATION);
        return ApiResponseCreator.success(new ApiMessage("로그아웃 되었습니다."));
    }


}
