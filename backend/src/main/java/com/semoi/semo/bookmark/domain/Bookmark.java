package com.semoi.semo.bookmark.domain;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "bookmarks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "board_id"})
})
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id", nullable = false, updatable = false)
    private Long bookmarkId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "boardId", nullable = false, updatable = false)
    private Board board;

    @Column(name = "craeted_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    private Bookmark(User user, Board board) {
        this.user = user;
        this.board = board;
    }

    public static Bookmark create(User user, Board board) {
        return new Bookmark(user, board);
    }
}
