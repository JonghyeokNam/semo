package com.semoi.semo.user.dto.request;

public record UserInfoRequestDto(
        String userEmail,
        String position,
        String campusName,
        String courseName
) {
}
