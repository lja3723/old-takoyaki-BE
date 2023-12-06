package com.bestbenefits.takoyaki.service;

import com.bestbenefits.takoyaki.DTO.client.request.UserAdditionalInfoReqDTO;
import com.bestbenefits.takoyaki.DTO.client.request.UserNicknameUpdateReqDTO;
import com.bestbenefits.takoyaki.DTO.layer.request.OAuthSignUpReqDTO;
import com.bestbenefits.takoyaki.DTO.layer.response.OAuthAuthResDTO;
import com.bestbenefits.takoyaki.DTO.client.request.UserDuplicateNicknameReqDTO;
import com.bestbenefits.takoyaki.entity.User;
import com.bestbenefits.takoyaki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean checkDuplicateNickname(UserDuplicateNicknameReqDTO userDuplicateNicknameReqDTO){
        return userRepository.findUserByNickname(userDuplicateNicknameReqDTO.getNickname()).orElse(null) != null;
    }

    @Transactional(readOnly = true)
    public OAuthAuthResDTO loginByOAuth(String email, int socialType){
        User user = userRepository.findUserByEmailAndSocial(email, socialType).orElse(null);
        if (user != null)
            return OAuthAuthResDTO.builder()
                    .id(user.getId())
                    .isInfoNeeded(user.getNickname() == null)
                    .build();
        else
            return null;
    }

    public OAuthAuthResDTO signUpByOAuth(OAuthSignUpReqDTO oAuthSignUpReqDTO){
        User user = userRepository.save(oAuthSignUpReqDTO.toEntity());
        return OAuthAuthResDTO.builder()
                .id(user.getId())
                .isInfoNeeded(true)
                .build();
    }

    @Transactional
    public void insertAdditionalInfo(Long id, UserAdditionalInfoReqDTO userAdditionalInfoReqDTO){
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found"));
        if (user.getNickname() != null)
            throw new IllegalStateException("User already has additional information");

        user.updateNickname(userAdditionalInfoReqDTO.getNickname());
        //another info...
    }

    @Transactional
    public void changeNickname(Long id, UserNicknameUpdateReqDTO userNicknameUpdateReqDTO){
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found"));

        String newNickname = userNicknameUpdateReqDTO.getNickname();

        user.updateNickname(newNickname);
    }

}