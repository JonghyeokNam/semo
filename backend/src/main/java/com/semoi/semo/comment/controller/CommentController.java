package com.semoi.semo.comment.controller;

import com.semoi.semo.comment.dto.CommentRequestDto;
import com.semoi.semo.comment.dto.CommentResponseDto;
import com.semoi.semo.comment.service.CommentService;
import com.semoi.semo.global.response.Response;
import com.semoi.semo.jwt.service.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final TokenProvider tokenProvider;

    @GetMapping("/boards/{boardId}/comments")
    @Operation(
            summary = "댓글 조회",
            description = "특정 게시글의 모든 댓글을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "댓글 조회 성공",
                    content = @Content(schema = @Schema(implementation = Response.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "게시글을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = Response.class))
            )
    })
    public Response<List<CommentResponseDto>> getComments(@PathVariable("boardId") Long boardId) {
        return Response.success(commentService.getAllComments(boardId));
    }

    @PostMapping("/boards/{boardId}/comments")
    @Operation(
            summary = "댓글 추가",
            description = "특정 게시글에 댓글을 추가합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "댓글 추가 성공",
                    content = @Content(schema = @Schema(implementation = Response.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = Response.class))
            )
    })
    public Response<Void> addComment(
            HttpServletRequest request,
            @PathVariable("boardId") Long boardId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        commentService.addComment(tokenProvider.getUserLoginEmail(request), boardId, commentRequestDto);
        return Response.success();
    }

    @PutMapping("/comments/{commentId}")
    @Operation(
            summary = "댓글 수정",
            description = "특정 댓글을 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "댓글 수정 성공",
                    content = @Content(schema = @Schema(implementation = Response.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "댓글을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = Response.class))
            )
    })
    public Response<Void> updateComment(
            HttpServletRequest request,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        commentService.updateComment(tokenProvider.getUserLoginEmail(request), commentId, commentRequestDto);
        return Response.success();
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(
            summary = "댓글 삭제",
            description = "특정 댓글을 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "댓글 삭제 성공",
                    content = @Content(schema = @Schema(implementation = Response.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "댓글을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = Response.class))
            )
    })
    public Response<Void> deleteComment(HttpServletRequest request, @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(tokenProvider.getUserLoginEmail(request), commentId);
        return Response.success();
    }
}
