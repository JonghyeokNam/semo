package com.semoi.semo.board.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semoi.semo.bookmark.domain.Bookmark;
import com.semoi.semo.comment.domain.Comment;
import com.semoi.semo.notification.entity.Notification;
import com.semoi.semo.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성자 (회원)

//    @Column(name = "user_id")
//    private Long userId;

    @Column(name = "hit", nullable = false)
    private Integer hit;

    @Column(name = "recruitment_types", nullable = false, length = 2000)
    private String recruitmentTypes; // JSON 문자열로 저장

    // Getter: JSON 문자열을 List<String>으로 변환
    public List<String> getRecruitmentTypes() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(recruitmentTypes, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            return Collections.emptyList(); // 변환 실패 시 빈 리스트 반환
        }
    }

    // Setter: List<String>을 JSON 문자열로 변환
    public void setRecruitmentTypes(List<String> recruitmentTypes) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.recruitmentTypes = objectMapper.writeValueAsString(recruitmentTypes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize recruitmentTypes", e);
        }
    }

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
