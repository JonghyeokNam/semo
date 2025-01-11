package com.semoi.semo.comment.domain;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.user.domain.User;
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

// 차현철
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
@SQLDelete(sql = "UPDATE comments SET deleted_at = SYSDATE WHERE comment_id = ?")
@Where(clause = "deleted_at IS NULL")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_form_seq ")
    @SequenceGenerator(name = "comment_form_seq ", sequenceName = "comment_form_seq", allocationSize = 1)
    @Column(name = "comment_id", nullable = false, updatable = false)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false, updatable = false)
    private Board board;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private Comment(String content, User user, Board board) {
        this.content = content;
        this.user = user;
        this.board = board;
    }

    public static Comment create(String content, User user, Board board) {
        return new Comment(content, user, board);
    }

    public void update(String content) {
        this.content = content;
    }
}
