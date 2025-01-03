package com.semoi.semo.user.domain;

import com.semoi.semo.position.entity.Position;
import com.semoi.semo.bookmark.domain.Bookmark;
import com.semoi.semo.campus.domain.Course;
import com.semoi.semo.comment.domain.Comment;
import com.semoi.semo.notification.entity.Notification;
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
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;

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
            Role role
    ) {
        this.username = username;
        this.loginEmail = loginEmail;
        this.userEmail = userEmail;
        this.role = role;
    }

    public static User create(String username, String loginEmail, String userEmail, Role role) {
        return new User(username, loginEmail, userEmail, role);
    }

    public void updateInfo(String username, String userEmail, Position position, Course course) {
        this.username = username;
        this.userEmail = userEmail;
        this.position = position;
        this.course = course;
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
