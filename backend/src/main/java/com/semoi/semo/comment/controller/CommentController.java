package com.semoi.semo.comment.controller;

import com.semoi.semo.comment.dto.CommentRequestDto;
import com.semoi.semo.comment.dto.CommentResponseDto;
import com.semoi.semo.comment.service.CommentService;
import com.semoi.semo.global.response.Response;
import com.semoi.semo.jwt.service.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final TokenProvider tokenProvider;

    @GetMapping("/boards/{boardId}/comments")
    public Response<List<CommentResponseDto>> getComments(@PathVariable("boardId") Long boardId) {
        return Response.success(commentService.getAllComments(boardId));
    }

    @PostMapping("/boards/{boardId}/comments")
    public Response<Void> addComment(
            HttpServletRequest request,
            @PathVariable("boardId") Long boardId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        commentService.addComment(tokenProvider.getUserLoginEmail(request), boardId, commentRequestDto);
        return Response.success();
    }

    @PutMapping("/comments/{commentId}")
    public Response<Void> updateComment(HttpServletRequest request,
                                        @PathVariable("commentId") Long commentId,
                                        @RequestBody CommentRequestDto commentRequestDto) {
        commentService.updateComment(tokenProvider.getUserLoginEmail(request), commentId, commentRequestDto);
        return Response.success();
    }

    @DeleteMapping("/comments/{commentId}")
    public Response<Void> deleteComment(HttpServletRequest request, @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(tokenProvider.getUserLoginEmail(request), commentId);
        return Response.success();
    }
}
