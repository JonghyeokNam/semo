package com.semoi.semo.user.service;

import com.semoi.semo.Campus.domain.Campus;
import com.semoi.semo.Campus.enums.CampusName;
import com.semoi.semo.Campus.service.CampusService;
import com.semoi.semo.user.domain.User;
import com.semoi.semo.user.dto.request.UserInfoRequestDto;
import com.semoi.semo.user.dto.response.UserResponseDto;
import com.semoi.semo.user.enums.Position;
import com.semoi.semo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final CampusService campusService;

    @Override
    public UserResponseDto createUser(String loginEmail, UserInfoRequestDto userInfoRequestDto) {
        CampusName campusName = CampusName.valueOf(userInfoRequestDto.campusName());
        Campus campus = campusService.getCampusOrElseThrow(campusName);
        Position position = Position.valueOf(userInfoRequestDto.position());
        User user = getUserByLoginEmailOrElseThrow(loginEmail);

        user.updateInfo(
                userInfoRequestDto.username(),
                userInfoRequestDto.userEmail(),
                position,
                campus
        );

        User saveUser = userRepository.save(user);

        return UserResponseDto.toDto(saveUser);
    }

    @Override
    public User getUserByUserIdOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    @Override
    public User getUserByLoginEmailOrElseThrow(String loginEmail) {
        return userRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    @Override
    public void resetUserScore() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            user.resetCurrentScore();
            userRepository.save(user);
        }
    }
}
