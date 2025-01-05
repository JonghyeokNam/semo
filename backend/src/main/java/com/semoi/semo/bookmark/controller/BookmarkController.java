package com.semoi.semo.bookmark.controller;

import com.semoi.semo.board.dto.responsedto.BoardResponseDto;
import com.semoi.semo.bookmark.service.BookmarkServiceImpl;
import com.semoi.semo.global.response.Response;
import com.semoi.semo.jwt.service.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
@Slf4j
public class BookmarkController {

    private final BookmarkServiceImpl bookmarkService;
    private final TokenProvider tokenProvider;

    @PostMapping("/boards/{boardId}")
    @Operation(
            summary = "게시글 북마크 추가/취소",
            description = "특정 게시글을 북마크에 추가하거나 취소합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "북마크 추가/취소 성공",
                    content = @Content(schema = @Schema(implementation = Response.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = Response.class))
            )
    })
    public Response<Void> addOrCancelBookmark(HttpServletRequest request, @PathVariable("boardId") Long boardId) {
        bookmarkService.addOrCancelBookmark(tokenProvider.getUserLoginEmail(request), boardId);
        return Response.success();
    }

    @GetMapping
    @Operation(
            summary = "북마크 목록 조회",
            description = "사용자가 북마크한 게시글의 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "북마크 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = Response.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = Response.class))
            )
    })
    public Response<List<BoardResponseDto>> getBookmarks(HttpServletRequest request) {
        return Response.success(bookmarkService.getBookmarksFromUser(tokenProvider.getUserLoginEmail(request)));
    }

    @GetMapping("/boards/{boardId}")
    @Operation(
            summary = "북마크 상태 확인",
            description = "특정 게시글에 대한 북마크 상태를 확인합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "북마크 상태 조회 성공",
                    content = @Content(schema = @Schema(implementation = Response.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "게시글을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = Response.class))
            )
    })
    public Response<Boolean> getState(HttpServletRequest request, @PathVariable("boardId") Long boardId) {
        return Response.success(bookmarkService.getState(tokenProvider.getUserLoginEmail(request), boardId));
    }
}
