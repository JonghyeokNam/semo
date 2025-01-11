package com.semoi.semo.notification.dto;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.notification.entity.Notification;

import java.time.LocalDateTime;

// 차현철
public record NotificationResponseDto(
        Long notificationId,
        String title,
        String content,
        boolean isRead,
        Long boardId,
        LocalDateTime createdAt
) {
    public static NotificationResponseDto fromEntity(Notification notification) {
        return new NotificationResponseDto(
                notification.getNotificationId(),
                notification.getType().getTitle(),
                notification.getType().getContent(),
                notification.isRead(),
                notification.getBoard().getBoardId(),
                notification.getCreatedAt()
        );
    }
}
