package com.semoi.semo.user.service;

import com.semoi.semo.user.domain.User;
import com.semoi.semo.user.dto.request.UserInfoRequestDto;
import com.semoi.semo.user.dto.response.UserResponseDto;

public interface UserService {
    User getUserByUserIdOrElseThrow(Long userId);
    User getUserByLoginEmailOrElseThrow(String loginEmail);
    UserResponseDto createUser(String loginEmail, UserInfoRequestDto userInfoRequestDto);

    void resetUserScore();
}
