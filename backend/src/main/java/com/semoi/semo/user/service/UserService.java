package com.semoi.semo.user.service;

import com.semoi.semo.user.domain.User;
import com.semoi.semo.user.dto.request.UserInfoRequestDto;
import com.semoi.semo.user.dto.response.UserResponseDto;

public interface UserService {
    User getUserByUserIdOrElseThrow(Long userId);
    User getUserByLoginEmailOrElseThrow(String loginEmail);
    UserResponseDto updateUser(String loginEmail, UserInfoRequestDto userInfoRequestDto);
    void resetUserScore();
    Boolean getCheckNewUser(String loginEmail);
}
