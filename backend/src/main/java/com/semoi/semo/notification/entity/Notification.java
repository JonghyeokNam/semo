package com.semoi.semo.notification.entity;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.notification.enums.Type;
import com.semoi.semo.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 차현철
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notifications")
@EntityListeners(AuditingEntityListener.class)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false, updatable = false)
    private Long notificationId;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false, updatable = false)
    private Board board;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    private Notification(Type type, User user, Board board) {
        this.type = type;
        this.user = user;
        this.board = board;
    }

    public static Notification create(Type type, User user, Board board) {
        return new Notification(type, user, board);
    }

    public void read() {
        this.isRead = true;
    }
}
