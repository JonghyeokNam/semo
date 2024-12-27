package com.semoi.semo.user.domain;

import com.semoi.semo.Campus.domain.Campus;
import com.semoi.semo.notification.entity.Notification;
import com.semoi.semo.user.enums.Position;
import com.semoi.semo.user.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = SYSDATE WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(name = "login_email", nullable = false, unique = true)
    private String loginEmail;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "rec_score", nullable = false)
    private int recScore = 0;

    @Column(name = "act_score", nullable = false)
    private int actScore = 0;

    @Column(name = "total_rec_score", nullable = false)
    private int totalRecScore = 0;

    @Column(name = "total_act_score", nullable = false)
    private int totalActScore = 0;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "campus_id")
    private Campus campus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Notification> notifications;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private User(
            String username,
            String loginEmail,
            String userEmail,
            Position position,
            Role role,
            Campus campus
    ) {
        this.username = username;
        this.loginEmail = loginEmail;
        this.userEmail = userEmail;
        this.position = position;
        this.role = role;
        this.campus = campus;
    }

    public static User create(String username, String loginEmail, String userEmail, Position position, Role role, Campus campus) {
        return new User(username, loginEmail, userEmail, position, role, campus);
    }

    public void updateInfo(String username, String userEmail, Position position, Campus campus) {
        this.username = username;
        this.userEmail = userEmail;
        this.position = position;
        this.campus = campus;
    }

    public void resetCurrentScore() {
        this.actScore = 0;
        this.recScore = 0;
    }

    public void plusActScore(int score) {
        this.totalActScore += score;
        this.actScore += score;
    }

    public void plusRecScore(int score) {
        this.totalRecScore += score;
        this.recScore += score;
    }
}
