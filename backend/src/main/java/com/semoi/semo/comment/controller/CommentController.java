package com.semoi.semo.comment.controller;

import com.semoi.semo.comment.dto.CommentRequestDto;
import com.semoi.semo.comment.dto.CommentResponseDto;
import com.semoi.semo.comment.service.CommentService;
import com.semoi.semo.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/boards/{boardId}/comments")
    public Response<List<CommentResponseDto>> getComments(@PathVariable Long boardId) {
        return Response.success(commentService.getAllComments(boardId));
    }

    @PostMapping("/boards/{boardId}/comments")
    public Response<Void> addComment(
            Authentication authentication,
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        commentService.addComment(authentication.getName(), boardId, commentRequestDto);
        return Response.success();
    }

    @PutMapping("/comments/{commentId}")
    public Response<Void> updateComment(Authentication authentication,
                                        @PathVariable Long commentId,
                                        @RequestBody CommentRequestDto commentRequestDto) {
        commentService.updateComment(authentication.getName(), commentId, commentRequestDto);
        return Response.success();
    }

    @DeleteMapping("/comments/{commentId}")
    public Response<Void> deleteComment(Authentication authentication, @PathVariable Long commentId) {
        commentService.deleteComment(authentication.getName(), commentId);
        return Response.success();
    }
}
