package com.semoi.semo.user.dto.response;

import com.semoi.semo.user.domain.User;

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
            campusName = user.getCourse().getCampus().getCampusName().getName();
        }

        if (user.getPosition() != null) {
            position = user.getPosition().getPosition();
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
