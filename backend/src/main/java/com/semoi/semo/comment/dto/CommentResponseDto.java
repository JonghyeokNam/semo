package com.semoi.semo.comment.dto;

import com.semoi.semo.comment.domain.Comment;
import com.semoi.semo.user.dto.response.UserResponseDto;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long commentId,
        String content,
        UserResponseDto user,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentResponseDto of(Comment comment) {
        return new CommentResponseDto(
                comment.getCommentId(),
                comment.getContent(),
                UserResponseDto.toDto(comment.getUser()),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}