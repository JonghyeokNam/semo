package com.semoi.semo.user.dto.request;

public record UserInfoRequestDto(
        String username,
        String userEmail,
        String position,
        String campusName
) {
}
