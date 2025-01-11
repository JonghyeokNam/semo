package com.semoi.semo.notification.service;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.notification.dto.NotificationResponseDto;
import com.semoi.semo.notification.enums.Type;
import com.semoi.semo.user.domain.User;

import java.util.List;

// 차현철
public interface NotificationService {

    boolean checkNotification(String loginEmail);

    List<NotificationResponseDto> getNotifications(String name);

    void readNotification(Long notificationId);

    void createNotification(Type type, User user, Board board);

}
