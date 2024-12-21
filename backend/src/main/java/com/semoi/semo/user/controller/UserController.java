package com.semoi.semo.user.controller;

import com.semoi.semo.global.response.Response;
import com.semoi.semo.jwt.service.TokenService;
import com.semoi.semo.oauth2.util.CookieUtil;
import com.semoi.semo.user.dto.request.UserInfoRequestDto;
import com.semoi.semo.user.dto.response.UserResponseDto;
import com.semoi.semo.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private static final String REFRESH_TOKEN_KEY_NAME = "refresh_token";

    @PutMapping
    public Response<UserResponseDto> saveInfo(Authentication authentication, @RequestBody UserInfoRequestDto userInfoRequestDto) {
        return Response.success(userService.createUser(authentication.getName(), userInfoRequestDto));
    }

    @GetMapping
    public Response<UserResponseDto> getLoginUser(Authentication authentication) {
        return Response.success(UserResponseDto.toDto(userService.getUserByLoginEmailOrElseThrow(authentication.getName())));
    }

    @PostMapping("/logout")
    public Response<Void> logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        tokenService.deleteRefreshToken(authentication.getName());

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_KEY_NAME);
        return Response.success();
    }
}
