package com.semoi.semo.user.service;

import com.semoi.semo.position.repository.PositionRepository;
import com.semoi.semo.campus.domain.Campus;
import com.semoi.semo.campus.domain.Course;
import com.semoi.semo.campus.repository.CourseRepository;
import com.semoi.semo.campus.service.CampusService;
import com.semoi.semo.global.exception.ErrorCode;
import com.semoi.semo.global.exception.SemoException;
import com.semoi.semo.user.domain.User;
import com.semoi.semo.user.dto.request.UserInfoRequestDto;
import com.semoi.semo.user.dto.response.UserResponseDto;
import com.semoi.semo.position.entity.Position;
import com.semoi.semo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final PositionRepository positionRepository;
    private final CampusService campusService;

    @Override
    public UserResponseDto updateUser(String loginEmail, UserInfoRequestDto userInfoRequestDto) {
        // 로그인 유저 조회
        User user = getUserByLoginEmailOrElseThrow(loginEmail);
        // 유저의 과정 조회
        Course course = user.getCourse();

        // 이미 등록된 과정의 수정을 막기 위해, null 경우에만 수정 가능.
        if (user.getCourse() == null) {
            Campus campus = campusService.getCampusOrElseThrow(userInfoRequestDto.campusName());

            String courseName = userInfoRequestDto.courseName();
            course = courseRepository.findByName(courseName)
                    .orElse(null);

            if (course == null) {
                course = Course.of(courseName, campus);
                courseRepository.save(course);
            }
        }

        // 유저가 입력한 position 조회
        Position position = positionRepository.findByName(userInfoRequestDto.position())
                        .orElseThrow(() -> new SemoException(ErrorCode.POSITION_NOT_FOUND));

        String userEmail = userInfoRequestDto.userEmail();
        validateEmailPattern(userEmail);

        // 유저 정보 수정
        user.updateInfo(
                userEmail,
                position,
                course
        );

        User saveUser = userRepository.save(user);

        return UserResponseDto.toDto(saveUser);
    }

    @Override
    // userId로 유저 조회, 실패 시 에러 발생
    public User getUserByUserIdOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new SemoException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    // loginEmail로 유저 조회, 실패 시 에러 발생
    public User getUserByLoginEmailOrElseThrow(String loginEmail) {
        return userRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> new SemoException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    // 유저의 점수를 초기화 하는 로직
    public void resetUserScore() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            user.resetCurrentScore();
            userRepository.save(user);
        }
    }

    @Override
    public Boolean getCheckNewUser(String loginEmail) {

        User user = getUserByLoginEmailOrElseThrow(loginEmail);

        // 유저의 포지션이나 과정이 입력되지 않았다면, 신규 유저로 판단.
        if (user.getPosition() == null || user.getCourse() == null) {
            return true;
        }

        return false;
    }

    // 이메일 패턴 유효성 검사
    private void validateEmailPattern(String email) {
        final String REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern regex = Pattern.compile(REGEX);
        Matcher matcher = regex.matcher(email);

        if (!matcher.matches()) {
            throw new SemoException(ErrorCode.UNSUITABLE_EMAIL, String.format("%s doesn't meet the conditions", email));
        }
    }
}
