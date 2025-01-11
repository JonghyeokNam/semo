package com.semoi.semo.user.dto.request;

// 차현철
public record UserRequestDto(
        String username,
        String loginEmail,
        String userEmail,
        String position,
        String campusName
) {
}
