package com.semoi.semo.bookmark.controller;

import com.semoi.semo.bookmark.dto.BookmarkResponseDto;
import com.semoi.semo.bookmark.service.BookmarkServiceImpl;
import com.semoi.semo.global.response.Response;
import com.semoi.semo.jwt.service.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
@Slf4j
public class BookmarkController {

    private final BookmarkServiceImpl bookmarkService;
    private final TokenProvider tokenProvider;

    @PostMapping("/boards/{boardId}")
    public Response<Void> addOrCancelBookmark(HttpServletRequest request, @PathVariable("boardId") Long boardId) {
        bookmarkService.addOrCancelBookmark(tokenProvider.getUserLoginEmail(request), boardId);
        return Response.success();
    }

    @GetMapping
    public Response<List<BookmarkResponseDto>> getBookmarks(HttpServletRequest request) {
        return Response.success(bookmarkService.getBookmarksFromUser(tokenProvider.getUserLoginEmail(request)));
    }

    @GetMapping("/boards/{boardId}")
    public Response<Boolean> getState(HttpServletRequest request, @PathVariable("boardId") Long boardId) {
        return Response.success(bookmarkService.getState(tokenProvider.getUserLoginEmail(request), boardId));
    }
}
