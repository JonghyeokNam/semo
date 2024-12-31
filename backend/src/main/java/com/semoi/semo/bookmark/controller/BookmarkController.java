package com.semoi.semo.bookmark.controller;

import com.semoi.semo.bookmark.dto.BookmarkResponseDto;
import com.semoi.semo.bookmark.service.BookmarkServiceImpl;
import com.semoi.semo.global.response.Response;
import com.semoi.semo.jwt.service.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
    public Response<Void> addOrCancelBookmark(HttpServletRequest request, @PathVariable Long boardId) {
        bookmarkService.addOrCancelBookmark(tokenProvider.getUserLoginEmail(request), boardId);
        return Response.success();
    }

    @GetMapping
    public Response<List<BookmarkResponseDto>> getBookmarks(HttpServletRequest request) {
        return Response.success(bookmarkService.getBookmarksFromUser(tokenProvider.getUserLoginEmail(request)));
    }

    @GetMapping("/boards/{boardId}")
    public Response<Boolean> getState(HttpServletRequest request, @PathVariable Long boardId) {
        return Response.success(bookmarkService.getState(tokenProvider.getUserLoginEmail(request), boardId));
    }
}
