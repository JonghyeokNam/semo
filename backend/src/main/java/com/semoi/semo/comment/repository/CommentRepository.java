package com.semoi.semo.comment.repository;

import com.semoi.semo.board.entity.Board;
import com.semoi.semo.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoard(Board board);

}
