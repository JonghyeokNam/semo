package com.semoi.semo.oauth2.service;

import com.semoi.semo.oauth2.dto.CustomOAuth2User;
import com.semoi.semo.oauth2.dto.KakaoResponse;
import com.semoi.semo.oauth2.dto.OAuth2Response;
import com.semoi.semo.user.domain.User;
import com.semoi.semo.user.enums.Role;
import com.semoi.semo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

// 차현철
@Service
@Log4j2
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        // 소셜로그인 인증 서버가 카카오인 경우.
        if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }

        String loginEmail = oAuth2Response.getEmail();

        User loginUser = userRepository.findByLoginEmail(loginEmail).orElse(null);

        // 신규 유저인 경우, DB에 저장
        if (loginUser == null) {
            User user = User.create(oAuth2Response.getName(), loginEmail, loginEmail, Role.USER);
            userRepository.save(user);
        }

        return new CustomOAuth2User(oAuth2Response, Role.USER.getRole());
    }
}