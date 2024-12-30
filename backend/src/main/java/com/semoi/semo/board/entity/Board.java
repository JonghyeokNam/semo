package com.semoi.semo.board.entity;

import com.semoi.semo.bookmark.domain.Bookmark;
import com.semoi.semo.comment.domain.Comment;
import com.semoi.semo.notification.entity.Notification;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", nullable = false, length = 4000)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Notification> notifications;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user; // 작성자 (회원)

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "hit", nullable = false)
    private Integer hit;

    @Column(name = "recruitment_type", nullable = false, length = 255)
    private String recruitmentType;

    @Column(name = "recruitment_count", nullable = false)
    private Integer recruitmentCount;

    @Column(name = "recruitment_field", nullable = false, length = 255)
    private String recruitmentField;

    @Column(name = "recruitment_method", nullable = false, length = 255)
    private String recruitmentMethod;

    @Column(name = "recruitment_deadline", nullable = false)
    private LocalDateTime recruitmentDeadline;

    @Column(name = "progress_period", nullable = false, length = 255)
    private String progressPeriod;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;
}
