package com.semoi.semo.notification.dto;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.notification.entity.Notification;

import java.time.LocalDateTime;

public record NotificationResponseDto(
        Long notificationId,
        String type,
        boolean isRead,
        Long boardId,
        LocalDateTime createdAt
) {
    public static NotificationResponseDto fromEntity(Notification notification) {
        return new NotificationResponseDto(
                notification.getNotificationId(),
                notification.getType().getType(),
                notification.isRead(),
                notification.getBoard().getBoardId(),
                notification.getCreatedAt()
        );
    }
}
