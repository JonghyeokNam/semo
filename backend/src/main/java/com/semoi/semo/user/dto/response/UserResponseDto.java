package com.semoi.semo.user.dto.response;

import com.semoi.semo.user.domain.User;

public record UserResponseDto(
        Long userId,
        String username,
        String userEmail,
        String position,
        String campusName,
        int recScore,
        int actScore
) {
    public static UserResponseDto toDto(User user) {
        String campusName = null;
        if (user.getCampus() != null) {
            campusName = user.getCampus().getCampusName().getName();
        }

        return new UserResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getUserEmail(),
                user.getPosition().getPosition(),
                campusName,
                user.getRecScore(),
                user.getActScore()
        );
    }
}
