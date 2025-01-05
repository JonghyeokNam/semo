package com.semoi.semo.user.dto.request;

public record UserRequestDto(
        String username,
        String loginEmail,
        String userEmail,
        String position,
        String campusName
) {
}
