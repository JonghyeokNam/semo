package com.semoi.semo.notification.service;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.global.exception.ErrorCode;
import com.semoi.semo.global.exception.SemoException;
import com.semoi.semo.notification.dto.NotificationResponseDto;
import com.semoi.semo.notification.entity.Notification;
import com.semoi.semo.notification.enums.Type;
import com.semoi.semo.notification.repository.NotificationRepository;
import com.semoi.semo.user.domain.User;
import com.semoi.semo.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

// 차현철
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public boolean checkNotification(String loginEmail) {
        User user = userService.getUserByLoginEmailOrElseThrow(loginEmail);
        return notificationRepository.existsByUserAndIsReadFalse(user);
    }

    @Override
    public void readNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new SemoException(ErrorCode.NOTIFICATION_NOT_FOUND));

        notification.read();
        notificationRepository.save(notification);
    }

    @Override
    public void createNotification(Type type, User user, Board board) {
        Notification notification = Notification.create(type, user, board);
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationResponseDto> getNotifications(String loginEmail) {
        User user = userService.getUserByLoginEmailOrElseThrow(loginEmail);

        return notificationRepository.findAllByUserOrderByCreatedAtDesc(user).stream()
                .map(NotificationResponseDto::fromEntity)
                .toList();
    }

}
