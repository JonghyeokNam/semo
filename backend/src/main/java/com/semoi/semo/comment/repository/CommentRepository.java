package com.semoi.semo.comment.repository;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.comment.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoard(Board board);

    List<Comment> findAllByBoardOrderByCreatedAtAsc(Board board);

    // 특정 게시글에 대한 댓글 수 계산
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.board.boardId = :boardId")
    int countByBoardId(@Param("boardId") Long boardId);
}
