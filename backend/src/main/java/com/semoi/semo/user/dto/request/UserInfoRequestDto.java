package com.semoi.semo.user.dto.request;

// 차현철
public record UserInfoRequestDto(
        String userEmail,
        String position,
        String campusName,
        String courseName
) {
}
