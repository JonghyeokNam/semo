package com.semoi.semo.jwt.service;

import com.semoi.semo.jwt.config.JwtProperties;
import com.semoi.semo.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // 토큰 생성 메서드
    public String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(user.getLoginEmail())
                .claim("role", user.getRole())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // 토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰 인증정보 조회 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authoritySet = Collections.singleton(
                new SimpleGrantedAuthority(claims.get("role").toString()));

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        claims.getSubject(),
                        "",
                        authoritySet
            ), token, authoritySet);
    }

    public String getUserLoginEmail(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);

        String accessToken = null;
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            accessToken = authorizationHeader.substring(TOKEN_PREFIX.length());
        }

        if (accessToken != null) {
            Claims claims = getClaims(accessToken);
            return claims.getSubject();
        }

        return null;
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
