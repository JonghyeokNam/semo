package com.semoi.semo.user.dto.response;

import com.semoi.semo.user.domain.User;

// 차현철
public record UserResponseDto(
        Long userId,
        String username,
        String userEmail,
        String position,
        String campusName,
        String courseName,
        int recScore,
        int actScore
) {
    public static UserResponseDto toDto(User user) {
        String campusName = null;
        String courseName = null;
        String position = null;

        if (user.getCourse() != null) {
            courseName = user.getCourse().getName();
            campusName = user.getCourse().getCampus().getName();
        }

        if (user.getPosition() != null) {
            position = user.getPosition().getName();
        }

        return new UserResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getUserEmail(),
                position,
                campusName,
                courseName,
                user.getRecScore(),
                user.getActScore()
        );
    }
}
