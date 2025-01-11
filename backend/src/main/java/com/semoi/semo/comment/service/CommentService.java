package com.semoi.semo.comment.service;

import com.semoi.semo.comment.dto.CommentRequestDto;
import com.semoi.semo.comment.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    List<CommentResponseDto> getAllComments(Long boardId);
    void addComment(String loginEmail, Long boardId, CommentRequestDto commentRequestDto);
    void updateComment(String loginEmail, Long commentId, CommentRequestDto commentRequestDto);
    void deleteComment(String loginEmail, Long commentId);
}
