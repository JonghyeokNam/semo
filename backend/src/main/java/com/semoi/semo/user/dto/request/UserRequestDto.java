package com.semoi.semo.user.dto.request;

import com.semoi.semo.Campus.domain.Campus;
import com.semoi.semo.user.domain.User;
import com.semoi.semo.user.enums.Position;
import com.semoi.semo.user.enums.Role;

public record UserRequestDto(
        String username,
        String loginEmail,
        String userEmail,
        String position,
        String campusName
) {
    public User toEntity(Position position, Campus campus) {
        return User.create(
            this.username,
            this.loginEmail,
            this.userEmail,
            position,
            Role.USER,
            campus
        );
    }
}
