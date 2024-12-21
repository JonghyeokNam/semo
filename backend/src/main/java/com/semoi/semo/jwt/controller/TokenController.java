package com.semoi.semo.jwt.controller;

import com.semoi.semo.global.response.Response;
import com.semoi.semo.jwt.service.TokenService;
import com.semoi.semo.oauth2.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping
    public Response<Void> createNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getCookie(request, "refresh_token")
                        .map(Cookie::getValue)
                        .orElse(null);

        String newAccessToken = tokenService.createNewAccessToken(refreshToken);

        response.setHeader("Authorization", "Bearer " + newAccessToken);

        return Response.success();
    }
}
